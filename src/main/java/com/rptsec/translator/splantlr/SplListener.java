// Generated from /Users/rohitprasad/spock/core/siem/src/main/java/com/rptsec/translator/splgrammar/Spl.g4 by ANTLR 4.9
package com.rptsec.translator.splantlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SplParser}.
 */
public interface SplListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SplParser#plusMinusOperator}.
	 * @param ctx the parse tree
	 */
	void enterPlusMinusOperator(SplParser.PlusMinusOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#plusMinusOperator}.
	 * @param ctx the parse tree
	 */
	void exitPlusMinusOperator(SplParser.PlusMinusOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(SplParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(SplParser.FieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#fieldWithWildChar}.
	 * @param ctx the parse tree
	 */
	void enterFieldWithWildChar(SplParser.FieldWithWildCharContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#fieldWithWildChar}.
	 * @param ctx the parse tree
	 */
	void exitFieldWithWildChar(SplParser.FieldWithWildCharContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#fieldWithOperator}.
	 * @param ctx the parse tree
	 */
	void enterFieldWithOperator(SplParser.FieldWithOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#fieldWithOperator}.
	 * @param ctx the parse tree
	 */
	void exitFieldWithOperator(SplParser.FieldWithOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#sortByClause}.
	 * @param ctx the parse tree
	 */
	void enterSortByClause(SplParser.SortByClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#sortByClause}.
	 * @param ctx the parse tree
	 */
	void exitSortByClause(SplParser.SortByClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#fieldList}.
	 * @param ctx the parse tree
	 */
	void enterFieldList(SplParser.FieldListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#fieldList}.
	 * @param ctx the parse tree
	 */
	void exitFieldList(SplParser.FieldListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#projectionField}.
	 * @param ctx the parse tree
	 */
	void enterProjectionField(SplParser.ProjectionFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#projectionField}.
	 * @param ctx the parse tree
	 */
	void exitProjectionField(SplParser.ProjectionFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#projectionFieldList}.
	 * @param ctx the parse tree
	 */
	void enterProjectionFieldList(SplParser.ProjectionFieldListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#projectionFieldList}.
	 * @param ctx the parse tree
	 */
	void exitProjectionFieldList(SplParser.ProjectionFieldListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#sortCommand}.
	 * @param ctx the parse tree
	 */
	void enterSortCommand(SplParser.SortCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#sortCommand}.
	 * @param ctx the parse tree
	 */
	void exitSortCommand(SplParser.SortCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#dedupCommand}.
	 * @param ctx the parse tree
	 */
	void enterDedupCommand(SplParser.DedupCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#dedupCommand}.
	 * @param ctx the parse tree
	 */
	void exitDedupCommand(SplParser.DedupCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#fieldsPlusMinus}.
	 * @param ctx the parse tree
	 */
	void enterFieldsPlusMinus(SplParser.FieldsPlusMinusContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#fieldsPlusMinus}.
	 * @param ctx the parse tree
	 */
	void exitFieldsPlusMinus(SplParser.FieldsPlusMinusContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#fieldsCommand}.
	 * @param ctx the parse tree
	 */
	void enterFieldsCommand(SplParser.FieldsCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#fieldsCommand}.
	 * @param ctx the parse tree
	 */
	void exitFieldsCommand(SplParser.FieldsCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#headCommand}.
	 * @param ctx the parse tree
	 */
	void enterHeadCommand(SplParser.HeadCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#headCommand}.
	 * @param ctx the parse tree
	 */
	void exitHeadCommand(SplParser.HeadCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#renamedField}.
	 * @param ctx the parse tree
	 */
	void enterRenamedField(SplParser.RenamedFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#renamedField}.
	 * @param ctx the parse tree
	 */
	void exitRenamedField(SplParser.RenamedFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#renameCommand}.
	 * @param ctx the parse tree
	 */
	void enterRenameCommand(SplParser.RenameCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#renameCommand}.
	 * @param ctx the parse tree
	 */
	void exitRenameCommand(SplParser.RenameCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#statsAggrOpWithCol}.
	 * @param ctx the parse tree
	 */
	void enterStatsAggrOpWithCol(SplParser.StatsAggrOpWithColContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#statsAggrOpWithCol}.
	 * @param ctx the parse tree
	 */
	void exitStatsAggrOpWithCol(SplParser.StatsAggrOpWithColContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#statsAggregationExpr}.
	 * @param ctx the parse tree
	 */
	void enterStatsAggregationExpr(SplParser.StatsAggregationExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#statsAggregationExpr}.
	 * @param ctx the parse tree
	 */
	void exitStatsAggregationExpr(SplParser.StatsAggregationExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#statsCommand}.
	 * @param ctx the parse tree
	 */
	void enterStatsCommand(SplParser.StatsCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#statsCommand}.
	 * @param ctx the parse tree
	 */
	void exitStatsCommand(SplParser.StatsCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#searchCommand}.
	 * @param ctx the parse tree
	 */
	void enterSearchCommand(SplParser.SearchCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#searchCommand}.
	 * @param ctx the parse tree
	 */
	void exitSearchCommand(SplParser.SearchCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#datasetSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterDatasetSpecifier(SplParser.DatasetSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#datasetSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitDatasetSpecifier(SplParser.DatasetSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(SplParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(SplParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#parseExp}.
	 * @param ctx the parse tree
	 */
	void enterParseExp(SplParser.ParseExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#parseExp}.
	 * @param ctx the parse tree
	 */
	void exitParseExp(SplParser.ParseExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(SplParser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(SplParser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#exprMulti}.
	 * @param ctx the parse tree
	 */
	void enterExprMulti(SplParser.ExprMultiContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#exprMulti}.
	 * @param ctx the parse tree
	 */
	void exitExprMulti(SplParser.ExprMultiContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#exprExplicitExpr}.
	 * @param ctx the parse tree
	 */
	void enterExprExplicitExpr(SplParser.ExprExplicitExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#exprExplicitExpr}.
	 * @param ctx the parse tree
	 */
	void exitExprExplicitExpr(SplParser.ExprExplicitExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#exprImplicitExpr}.
	 * @param ctx the parse tree
	 */
	void enterExprImplicitExpr(SplParser.ExprImplicitExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#exprImplicitExpr}.
	 * @param ctx the parse tree
	 */
	void exitExprImplicitExpr(SplParser.ExprImplicitExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(SplParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(SplParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#cmpExplicitExpr}.
	 * @param ctx the parse tree
	 */
	void enterCmpExplicitExpr(SplParser.CmpExplicitExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#cmpExplicitExpr}.
	 * @param ctx the parse tree
	 */
	void exitCmpExplicitExpr(SplParser.CmpExplicitExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#cmpImplicitExpr}.
	 * @param ctx the parse tree
	 */
	void enterCmpImplicitExpr(SplParser.CmpImplicitExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#cmpImplicitExpr}.
	 * @param ctx the parse tree
	 */
	void exitCmpImplicitExpr(SplParser.CmpImplicitExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#justCmp}.
	 * @param ctx the parse tree
	 */
	void enterJustCmp(SplParser.JustCmpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#justCmp}.
	 * @param ctx the parse tree
	 */
	void exitJustCmp(SplParser.JustCmpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#cmp}.
	 * @param ctx the parse tree
	 */
	void enterCmp(SplParser.CmpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#cmp}.
	 * @param ctx the parse tree
	 */
	void exitCmp(SplParser.CmpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(SplParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(SplParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#expressionT}.
	 * @param ctx the parse tree
	 */
	void enterExpressionT(SplParser.ExpressionTContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#expressionT}.
	 * @param ctx the parse tree
	 */
	void exitExpressionT(SplParser.ExpressionTContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#cmpLHS}.
	 * @param ctx the parse tree
	 */
	void enterCmpLHS(SplParser.CmpLHSContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#cmpLHS}.
	 * @param ctx the parse tree
	 */
	void exitCmpLHS(SplParser.CmpLHSContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#cmpRHS}.
	 * @param ctx the parse tree
	 */
	void enterCmpRHS(SplParser.CmpRHSContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#cmpRHS}.
	 * @param ctx the parse tree
	 */
	void exitCmpRHS(SplParser.CmpRHSContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(SplParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(SplParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#booleanOperator}.
	 * @param ctx the parse tree
	 */
	void enterBooleanOperator(SplParser.BooleanOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#booleanOperator}.
	 * @param ctx the parse tree
	 */
	void exitBooleanOperator(SplParser.BooleanOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterComparator(SplParser.ComparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitComparator(SplParser.ComparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#fieldValueList}.
	 * @param ctx the parse tree
	 */
	void enterFieldValueList(SplParser.FieldValueListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#fieldValueList}.
	 * @param ctx the parse tree
	 */
	void exitFieldValueList(SplParser.FieldValueListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#substringPlaceholder}.
	 * @param ctx the parse tree
	 */
	void enterSubstringPlaceholder(SplParser.SubstringPlaceholderContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#substringPlaceholder}.
	 * @param ctx the parse tree
	 */
	void exitSubstringPlaceholder(SplParser.SubstringPlaceholderContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#joinStr}.
	 * @param ctx the parse tree
	 */
	void enterJoinStr(SplParser.JoinStrContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#joinStr}.
	 * @param ctx the parse tree
	 */
	void exitJoinStr(SplParser.JoinStrContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#joinType}.
	 * @param ctx the parse tree
	 */
	void enterJoinType(SplParser.JoinTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#joinType}.
	 * @param ctx the parse tree
	 */
	void exitJoinType(SplParser.JoinTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#joinTypeValue}.
	 * @param ctx the parse tree
	 */
	void enterJoinTypeValue(SplParser.JoinTypeValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#joinTypeValue}.
	 * @param ctx the parse tree
	 */
	void exitJoinTypeValue(SplParser.JoinTypeValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#joinConstraints}.
	 * @param ctx the parse tree
	 */
	void enterJoinConstraints(SplParser.JoinConstraintsContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#joinConstraints}.
	 * @param ctx the parse tree
	 */
	void exitJoinConstraints(SplParser.JoinConstraintsContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#leftAlias}.
	 * @param ctx the parse tree
	 */
	void enterLeftAlias(SplParser.LeftAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#leftAlias}.
	 * @param ctx the parse tree
	 */
	void exitLeftAlias(SplParser.LeftAliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#rightAlias}.
	 * @param ctx the parse tree
	 */
	void enterRightAlias(SplParser.RightAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#rightAlias}.
	 * @param ctx the parse tree
	 */
	void exitRightAlias(SplParser.RightAliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#columnName}.
	 * @param ctx the parse tree
	 */
	void enterColumnName(SplParser.ColumnNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#columnName}.
	 * @param ctx the parse tree
	 */
	void exitColumnName(SplParser.ColumnNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(SplParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(SplParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#joinEqualities}.
	 * @param ctx the parse tree
	 */
	void enterJoinEqualities(SplParser.JoinEqualitiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#joinEqualities}.
	 * @param ctx the parse tree
	 */
	void exitJoinEqualities(SplParser.JoinEqualitiesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#namedDataset}.
	 * @param ctx the parse tree
	 */
	void enterNamedDataset(SplParser.NamedDatasetContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#namedDataset}.
	 * @param ctx the parse tree
	 */
	void exitNamedDataset(SplParser.NamedDatasetContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#dataset}.
	 * @param ctx the parse tree
	 */
	void enterDataset(SplParser.DatasetContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#dataset}.
	 * @param ctx the parse tree
	 */
	void exitDataset(SplParser.DatasetContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#lookupCommand}.
	 * @param ctx the parse tree
	 */
	void enterLookupCommand(SplParser.LookupCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#lookupCommand}.
	 * @param ctx the parse tree
	 */
	void exitLookupCommand(SplParser.LookupCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#lookupMatchOn}.
	 * @param ctx the parse tree
	 */
	void enterLookupMatchOn(SplParser.LookupMatchOnContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#lookupMatchOn}.
	 * @param ctx the parse tree
	 */
	void exitLookupMatchOn(SplParser.LookupMatchOnContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#lookupOutput}.
	 * @param ctx the parse tree
	 */
	void enterLookupOutput(SplParser.LookupOutputContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#lookupOutput}.
	 * @param ctx the parse tree
	 */
	void exitLookupOutput(SplParser.LookupOutputContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#lookupOptionLocal}.
	 * @param ctx the parse tree
	 */
	void enterLookupOptionLocal(SplParser.LookupOptionLocalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#lookupOptionLocal}.
	 * @param ctx the parse tree
	 */
	void exitLookupOptionLocal(SplParser.LookupOptionLocalContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#lookupOptionUpdate}.
	 * @param ctx the parse tree
	 */
	void enterLookupOptionUpdate(SplParser.LookupOptionUpdateContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#lookupOptionUpdate}.
	 * @param ctx the parse tree
	 */
	void exitLookupOptionUpdate(SplParser.LookupOptionUpdateContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#lookupTableName}.
	 * @param ctx the parse tree
	 */
	void enterLookupTableName(SplParser.LookupTableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#lookupTableName}.
	 * @param ctx the parse tree
	 */
	void exitLookupTableName(SplParser.LookupTableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#lookupField}.
	 * @param ctx the parse tree
	 */
	void enterLookupField(SplParser.LookupFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#lookupField}.
	 * @param ctx the parse tree
	 */
	void exitLookupField(SplParser.LookupFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#lookupEventField}.
	 * @param ctx the parse tree
	 */
	void enterLookupEventField(SplParser.LookupEventFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#lookupEventField}.
	 * @param ctx the parse tree
	 */
	void exitLookupEventField(SplParser.LookupEventFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#lookupDestField}.
	 * @param ctx the parse tree
	 */
	void enterLookupDestField(SplParser.LookupDestFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#lookupDestField}.
	 * @param ctx the parse tree
	 */
	void exitLookupDestField(SplParser.LookupDestFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link SplParser#lookupDestFieldAlias}.
	 * @param ctx the parse tree
	 */
	void enterLookupDestFieldAlias(SplParser.LookupDestFieldAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link SplParser#lookupDestFieldAlias}.
	 * @param ctx the parse tree
	 */
	void exitLookupDestFieldAlias(SplParser.LookupDestFieldAliasContext ctx);
}
