grammar Spl;
fragment LOWERCASE  : [a-z] ;
fragment UPPERCASE  : [A-Z] ;
fragment DIGIT: '0'..'9' ;
fragment BOOL: ('true' | 'false' | 'TRUE' | 'FALSE');
BOOLEAN: BOOL;
Join: 'JOIN';
Where: ('WHERE' | 'where');
WS: [ \n\t\r];
WSC: [ \n\t\r,];
NUM: (DIGIT)+;
ALPHANUM: (LOWERCASE | UPPERCASE | DIGIT | '_')+;
ALPHANUM_QUOTES: ('"' (LOWERCASE | UPPERCASE | DIGIT | '/'|'\\'|'.'|'_'|':'|' ')*  '"');
ALPHANUM_QUOTES_WC: ('"' (LOWERCASE | UPPERCASE | DIGIT | '/'|'\\'|'.'|'_'|':'|'*'|' '|'?')*  '"');
ALPHANUM_WC: (LOWERCASE | UPPERCASE | DIGIT | '_' | '*')+;

// Grammars used by more than one Command
plusMinusOperator: '+'|'-';
field: ALPHANUM;
fieldWithWildChar: '\'' ALPHANUM_WC '\'';
fieldWithOperator: (plusMinusOperator)? WS* field;
sortByClause:  (fieldWithOperator WS* ','? WS*)+ ;
fieldList: (WS* field WS* ','? WS*)+;
projectionField: (field | fieldWithWildChar);
projectionFieldList: (WS* projectionField WS* ','? WS*)+;

// SORT command
// Accepted examples:
//      ... | sort _time, -host
//      ... | sort 100 -size, +source
//      ... | sort - host
// Not accepted (yet):
//      ... | sort 100 -num(size), +str(source)
//      ... | sort limit=12 host
sortCommand:
        ('sort' | 'SORT') (WS+ sortCount=NUM)? WS+ sortByClause (WS+ 'desc')?
    ;

// DEDUP command
// https://docs.splunk.com/Documentation/SCS/current/SearchReference/dedupcommandsyntaxdetails
// https://docs.splunk.com/Documentation/Splunk/latest/SearchReference/dedup#Syntax
// Accepted examples:
//      ... | dedup 2 source, host
//      ... | dedup consecutive=true source, host
//      ... | dedup source sortby -_size
//      ... | dedup keepempty=true blah sortby  -field1, field2, +field3
//      ... | dedup blah  keepempty=true sortby  -field1, field2, +field3
//      ... | dedup blah, foo1 keepempty=true sortby  -field1, field2, +field3
dedupCommand:
        ('dedup' | 'DEDUP') (WS+ firstN=NUM)?
        (WS+ 'keepevents=' BOOLEAN)? (WS+ 'keepempty=' BOOLEAN)? (WS+ 'consecutive=' BOOLEAN)?
        WS+ fieldList
        (WS+ 'keepevents=' BOOLEAN)? (WS+ 'keepempty=' BOOLEAN)? (WS+ 'consecutive=' BOOLEAN)?
        (WS+ 'sortby' WS+ sortByClause)?
        WS* EOF;

// FIELDS command
// https://docs.splunk.com/Documentation/SCS/current/SearchReference/FieldsCommandSyntaxDetails
// Accepted examples:
//      ... | fields host, src
//      ... | fields - host, ip
//      ... | fields source, sourcetype, host, 'error*'
fieldsPlusMinus: '+' | '-';
fieldsCommand:
        ('fields' | 'FIELDS') (WS+ fieldsPlusMinus)? WS+ projectionFieldList
        WS* EOF;

// HEAD command
// https://docs.splunk.com/Documentation/SCS/current/SearchReference/HeadCommandSyntaxDetails
// Accepted examples:
//      ... | head
//      ... | head 3
// Not supported (yet)
//      ... | head while (user=garcia) 5
headCommand:
        ('head' | 'HEAD') (WS+ count=NUM)?
        WS* EOF;

// RENAME command
// https://docs.splunk.com/Documentation/SCS/current/SearchReference/RenameCommandSyntaxDetails
// Accepted examples:
//      ... | rename usr AS username
//      ... | rename usr AS username, dpt AS department
// Not supported (yet)
//      ... | rename 'ip-add' AS IPAddress
renamedField: (sourceField=field WS+ ('AS'|'as') WS+ targetField=field WS* ','?  WS*);
renameCommand:
        ('rename' | 'RENAME') WS+ renamedField+
        WS* EOF;

// STATS command
// Accepted examples:
//      ... | stats sum(bytes) BY host1
//      ... | stats sum(bytes) AS SumOfBytes, avg(bytes) AS Average BY host1, src
// Not supported (yet)
//      ... | stats sum(bytes) AS 'Sum of bytes', avg(bytes) AS Average BY host1, src
statsAggrOpWithCol: ('count' | 'sum' | 'avg') '(' field ')';
statsAggregationExpr:
        (WS* statsAggrOpWithCol (WS+ ('AS' | 'as') WS+ field)? WS* ','?);
statsCommand:
        ('stats' | 'STATS') WS+ statsAggregationExpr+ (WS+ ('BY'|'by') WS+ fieldList)?
        WS* EOF;

// SEARCH command
// Following examples are working -
//      SEARCH source=tablename ((f1=5 AND f1=6) OR [SS])
//      source=tablename ((f1=5 AND f1=6) OR [SS])
//      source=tablename ((f1=5 AND f1=6))
//      source=tablename f1=6
//      source=tablename f1=6 OR f2=5
//      source=tablename f1 = 6 f2 >= 5
//      source=tablename f1 = 6 [SS] f2=7 AND fr=4
searchCommand: ('SEARCH' WS+)? datasetSpecifier WS+ whereClause WS* EOF
    |   '(' searchCommand ')';
datasetSpecifier: ('sourcetype' | 'host' | 'source' | 'index') '=' datasetName=ALPHANUM;
//whereClause: expression;
whereClause: parseExp;

// https://stackoverflow.com/questions/30976962/nested-boolean-expression-parser-using-antlr
parseExp: exp;
exp: '(' exp ')'
    |   'NOT' WS+ exp
    |   left=exp WS+ booleanOperator WS+ right=exp
    |   left=exp WS+ right=exp
    |   leftC=cmp WS+ booleanOperator WS+ rightC=cmp
    |   leftC=cmp WS+ rightC=cmp
    |   cmp
;


exprMulti:
        ('(' exprMulti ')') EOF
    |   cmpExplicitExpr
    |   cmpImplicitExpr
    |   justCmp EOF
    |   exprExplicitExpr
    |   exprImplicitExpr;
exprExplicitExpr:   (expr WS+ booleanOperator WS+ exprMulti) | (expr WS+ booleanOperator WS+ '(' exprMulti ')');
exprImplicitExpr:   expr WS+ exprMulti;
expr:
        '(' expr ')'
    |   cmpExplicitExpr
    |   cmpImplicitExpr
    |   justCmp
    ;
cmpExplicitExpr: (cmp WS+ booleanOperator WS+ exprMulti) | ('('cmp')' WS+ booleanOperator WS+ exprMulti);
cmpImplicitExpr: cmp WS+ exprMulti;
justCmp: cmp;
cmp: (cmpLHS op=comparator cmpRHS) | ('(' cmp ')') | '[SS]';

expression:
        expressionT WS+ booleanOperator WS+ expressionT
    | expression WS+ booleanOperator WS+ expressionT
    | expressionT WS+ booleanOperator WS+ expression
    | expression WS+ expression
    |   expressionT
    |   '(' expression ')'
    ;
expressionT:
        comparison
    |   '(' comparison ')'
    |   '(' expressionT ')'
    |   (comparison WS* booleanOperator WS* comparison)
    |   (comparison (WS+ comparison)*)+                        // Needed for the implicit AND i.e. user=adam age>10
    ;
cmpLHS: ALPHANUM;
cmpRHS: (ALPHANUM_QUOTES | ALPHANUM  | NUM | ALPHANUM_QUOTES_WC);
comparison:
        (cmpLHS op=comparator cmpRHS)
    |   (fieldNameIn=ALPHANUM WS* 'IN' WS* fieldValueList)
    |   substringPlaceholder;
booleanOperator:
        'AND' | 'OR';
comparator: '='|'!='|'<'|'<='|'>'|'>=';
fieldValueList: '(' WS* fieldValue1=ALPHANUM (WS* ',' WS* fieldValueN=ALPHANUM)* WS* ')';
substringPlaceholder: '[SS]';




// JOIN command
// https://docs.splunk.com/Documentation/SCS/current/SearchReference/JoinCommandSyntaxDetails
joinStr: Join WS+ joinType? WS+ joinConstraints WS+ dataset;
joinType: 'type=' joinTypeValue;
joinTypeValue: ('inner' | 'outer' | 'left');
joinConstraints: ('left='leftAlias)? WS+ ('right='rightAlias)? WS+ Where WS+ joinEqualities  ;
leftAlias: ALPHANUM;
rightAlias: ALPHANUM;
columnName: ALPHANUM;
condition: leftAlias'.'columnName'='rightAlias'.'columnName;
joinEqualities: condition;  // TODO: Need to handle mulitple conditions with brackets!
namedDataset: ALPHANUM;
// unnamedDataset: '['ANY+']';
dataset: namedDataset;


// LOOKUP command
// Splunk syntax - https://docs.splunk.com/Documentation/Splunk/8.1.1/SearchReference/Lookup
//       lookup
//         [local=<bool>]
//         [update=<bool>]
//         <lookup-table-name>
//         ( <lookup-field> [AS <event-field>] )...
//         [ OUTPUT | OUTPUTNEW (<lookup-destfield> [AS <event-destfield>] )... ]
// BNF from https://gist.github.com/ChrisYounger/e51f9c3aba0f1ed02e5caee7d4a6128b#file-splunk_searchbnf-L2283
//      lookup (local=<bool>)? (update=<bool>)? (event_time_field=<string>)?
//          <string:lookup-table-name> (<field:lookup> (as <field:local>)? )+
//          (OUTPUT|OUTPUTNEW (<field:dest> (as <field:local-dest>)? )+ )?

lookupCommand: ('lookup' | 'LOOKUP') (WS+ 'local=' lookupOptionLocal)?
    (WS+ 'update=' lookupOptionUpdate)? WS+ lookupTableName lookupMatchOn
    (WS+ ('OUTPUT'|'OUTPUTNEW') lookupOutput )?;
lookupMatchOn: (WS+ lookupField (WS+ ('as'|'AS')  WS+ lookupEventField)?) (WS* ',' WS+ lookupField (WS+ ('as'|'AS')  WS+ lookupEventField)?)*;  // Need to account for space in between multiple occurences.
lookupOutput: (WS+ lookupDestField (WS+ ('as'|'AS')  WS+ lookupDestFieldAlias)?) (WS* ',' WS+ lookupDestField (WS+ ('as'|'AS')  WS+ lookupDestFieldAlias)? )*;
lookupOptionLocal: BOOL;
lookupOptionUpdate: BOOL;
lookupTableName: ALPHANUM;
lookupField: ALPHANUM;
lookupEventField: ALPHANUM;
lookupDestField: ALPHANUM;
lookupDestFieldAlias: ALPHANUM;
// Working examples:
//      lookup usertogroup user as local_user OUTPUT group as user_group
//      lookup status_desc status OUTPUT description
//      lookup usertogroup user as local_user pr as pr_owner OUTPUT group as user_group

