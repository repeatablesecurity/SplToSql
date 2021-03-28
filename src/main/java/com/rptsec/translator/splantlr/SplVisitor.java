// Generated from /Users/rohitprasad/spock/core/siem/src/main/java/com/rptsec/translator/splgrammar/Spl.g4 by ANTLR 4.9
package com.rptsec.translator.splantlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SplParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SplVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SplParser#plusMinusOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlusMinusOperator(SplParser.PlusMinusOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitField(SplParser.FieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#fieldWithWildChar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldWithWildChar(SplParser.FieldWithWildCharContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#fieldWithOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldWithOperator(SplParser.FieldWithOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#sortByClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSortByClause(SplParser.SortByClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#fieldList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldList(SplParser.FieldListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#projectionField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProjectionField(SplParser.ProjectionFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#projectionFieldList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProjectionFieldList(SplParser.ProjectionFieldListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#sortCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSortCommand(SplParser.SortCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#dedupCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDedupCommand(SplParser.DedupCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#fieldsPlusMinus}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldsPlusMinus(SplParser.FieldsPlusMinusContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#fieldsCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldsCommand(SplParser.FieldsCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#headCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeadCommand(SplParser.HeadCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#renamedField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenamedField(SplParser.RenamedFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#renameCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenameCommand(SplParser.RenameCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#statsAggrOpWithCol}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatsAggrOpWithCol(SplParser.StatsAggrOpWithColContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#statsAggregationExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatsAggregationExpr(SplParser.StatsAggregationExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#statsCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatsCommand(SplParser.StatsCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#searchCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSearchCommand(SplParser.SearchCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#datasetSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatasetSpecifier(SplParser.DatasetSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(SplParser.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#parseExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParseExp(SplParser.ParseExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(SplParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#exprMulti}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprMulti(SplParser.ExprMultiContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#exprExplicitExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprExplicitExpr(SplParser.ExprExplicitExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#exprImplicitExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprImplicitExpr(SplParser.ExprImplicitExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(SplParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#cmpExplicitExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmpExplicitExpr(SplParser.CmpExplicitExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#cmpImplicitExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmpImplicitExpr(SplParser.CmpImplicitExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#justCmp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJustCmp(SplParser.JustCmpContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#cmp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmp(SplParser.CmpContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(SplParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#expressionT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionT(SplParser.ExpressionTContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#cmpLHS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmpLHS(SplParser.CmpLHSContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#cmpRHS}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmpRHS(SplParser.CmpRHSContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(SplParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#booleanOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanOperator(SplParser.BooleanOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparator(SplParser.ComparatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#fieldValueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldValueList(SplParser.FieldValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#substringPlaceholder}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubstringPlaceholder(SplParser.SubstringPlaceholderContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#joinStr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinStr(SplParser.JoinStrContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#joinType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinType(SplParser.JoinTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#joinTypeValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinTypeValue(SplParser.JoinTypeValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#joinConstraints}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinConstraints(SplParser.JoinConstraintsContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#leftAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftAlias(SplParser.LeftAliasContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#rightAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRightAlias(SplParser.RightAliasContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#columnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnName(SplParser.ColumnNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(SplParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#joinEqualities}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinEqualities(SplParser.JoinEqualitiesContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#namedDataset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamedDataset(SplParser.NamedDatasetContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#dataset}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataset(SplParser.DatasetContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#lookupCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupCommand(SplParser.LookupCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#lookupMatchOn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupMatchOn(SplParser.LookupMatchOnContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#lookupOutput}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupOutput(SplParser.LookupOutputContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#lookupOptionLocal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupOptionLocal(SplParser.LookupOptionLocalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#lookupOptionUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupOptionUpdate(SplParser.LookupOptionUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#lookupTableName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupTableName(SplParser.LookupTableNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#lookupField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupField(SplParser.LookupFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#lookupEventField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupEventField(SplParser.LookupEventFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#lookupDestField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupDestField(SplParser.LookupDestFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link SplParser#lookupDestFieldAlias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLookupDestFieldAlias(SplParser.LookupDestFieldAliasContext ctx);
}
