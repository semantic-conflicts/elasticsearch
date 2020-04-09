package org.elasticsearch.index.query;
import com.carrotsearch.hppc.ObjectFloatHashMap;
import com.google.common.collect.Lists;
import org.elasticsearch.ElasticsearchParseException;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.ParseFieldMatcher;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.search.MatchQuery;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
/** 
 * Same as  {@link MatchQueryBuilder} but supports multiple fields.
 */
public class MultiMatchQueryBuilder extends QueryBuilder implements BoostableQueryBuilder<MultiMatchQueryBuilder> {
  public Object text;
  public List<String> fields;
  public ObjectFloatHashMap<String> fieldsBoosts;
  public MultiMatchQueryBuilder.Type type;
  public MatchQueryBuilder.Operator operator;
  public String analyzer;
  public Float boost;
  public Integer slop;
  public Fuzziness fuzziness;
  public Integer prefixLength;
  public Integer maxExpansions;
  public String minimumShouldMatch;
  public String rewrite=null;
  public String fuzzyRewrite=null;
  public Boolean useDisMax;
  public Float tieBreaker;
  public Boolean lenient;
  public Float cutoffFrequency=null;
  public MatchQueryBuilder.ZeroTermsQuery zeroTermsQuery=null;
  public String queryName;
  public enum Type {  /** 
 * Uses the best matching boolean field as main score and uses a tie-breaker to adjust the score based on remaining field matches
 */
  BEST_FIELDS(MatchQuery.Type.BOOLEAN,0.0f,new ParseField("best_fields","boolean")),   /** 
 * Uses the sum of the matching boolean fields to score the query
 */
  MOST_FIELDS(MatchQuery.Type.BOOLEAN,1.0f,new ParseField("most_fields")),   /** 
 * Uses a blended DocumentFrequency to dynamically combine the queried fields into a single field given the configured analysis is identical. This type uses a tie-breaker to adjust the score based on remaining matches per analyzed terms
 */
  CROSS_FIELDS(MatchQuery.Type.BOOLEAN,0.0f,new ParseField("cross_fields")),   /** 
 * Uses the best matching phrase field as main score and uses a tie-breaker to adjust the score based on remaining field matches
 */
  PHRASE(MatchQuery.Type.PHRASE,0.0f,new ParseField("phrase")),   /** 
 * Uses the best matching phrase-prefix field as main score and uses a tie-breaker to adjust the score based on remaining field matches
 */
  PHRASE_PREFIX(MatchQuery.Type.PHRASE_PREFIX,0.0f,new ParseField("phrase_prefix"));   public MatchQuery.Type matchQueryType;
  public float tieBreaker;
  public ParseField parseField;
  Type(  MatchQuery.Type matchQueryType,  float tieBreaker,  ParseField parseField){
    this.matchQueryType=matchQueryType;
    this.tieBreaker=tieBreaker;
    this.parseField=parseField;
  }
  public float tieBreaker(){
    return this.tieBreaker;
  }
  public MatchQuery.Type matchQueryType(){
    return matchQueryType;
  }
  public ParseField parseField(){
    return parseField;
  }
  public static Type parse(  String value,  ParseFieldMatcher parseFieldMatcher){
    MultiMatchQueryBuilder.Type[] values=MultiMatchQueryBuilder.Type.values();
    Type type=null;
    for (    MultiMatchQueryBuilder.Type t : values) {
      if (parseFieldMatcher.match(value,t.parseField())) {
        type=t;
        break;
      }
    }
    if (type == null) {
      throw new ElasticsearchParseException("failed to parse [{}] query type [{}]. unknown type.",MultiMatchQueryParser.NAME,value);
    }
    return type;
  }
}
  /** 
 * Constructs a new text query.
 */
  public MultiMatchQueryBuilder(  Object text,  String... fields){
    this.fields=Lists.newArrayList();
    this.fields.addAll(Arrays.asList(fields));
    this.text=text;
  }
  /** 
 * Adds a field to run the multi match against.
 */
  public MultiMatchQueryBuilder field(  String field){
    fields.add(field);
    return this;
  }
  /** 
 * Adds a field to run the multi match against with a specific boost.
 */
  public MultiMatchQueryBuilder field(  String field,  float boost){
    fields.add(field);
    if (fieldsBoosts == null) {
      fieldsBoosts=new ObjectFloatHashMap<>();
    }
    fieldsBoosts.put(field,boost);
    return this;
  }
  /** 
 * Sets the type of the text query.
 */
  public MultiMatchQueryBuilder type(  MultiMatchQueryBuilder.Type type){
    this.type=type;
    return this;
  }
  /** 
 * Sets the type of the text query.
 */
  public MultiMatchQueryBuilder type(  Object type){
    this.type=type == null ? null : Type.parse(type.toString().toLowerCase(Locale.ROOT),ParseFieldMatcher.EMPTY);
    return this;
  }
  /** 
 * Sets the operator to use when using a boolean query. Defaults to <tt>OR</tt>.
 */
  public MultiMatchQueryBuilder operator(  MatchQueryBuilder.Operator operator){
    this.operator=operator;
    return this;
  }
  /** 
 * Explicitly set the analyzer to use. Defaults to use explicit mapping config for the field, or, if not set, the default search analyzer.
 */
  public MultiMatchQueryBuilder analyzer(  String analyzer){
    this.analyzer=analyzer;
    return this;
  }
  /** 
 * Set the boost to apply to the query.
 */
  @Override public MultiMatchQueryBuilder boost(  float boost){
    this.boost=boost;
    return this;
  }
  /** 
 * Set the phrase slop if evaluated to a phrase query type.
 */
  public MultiMatchQueryBuilder slop(  int slop){
    this.slop=slop;
    return this;
  }
  /** 
 * Sets the fuzziness used when evaluated to a fuzzy query type. Defaults to "AUTO".
 */
  public MultiMatchQueryBuilder fuzziness(  Object fuzziness){
    this.fuzziness=Fuzziness.build(fuzziness);
    return this;
  }
  public MultiMatchQueryBuilder prefixLength(  int prefixLength){
    this.prefixLength=prefixLength;
    return this;
  }
  /** 
 * When using fuzzy or prefix type query, the number of term expansions to use. Defaults to unbounded so its recommended to set it to a reasonable value for faster execution.
 */
  public MultiMatchQueryBuilder maxExpansions(  int maxExpansions){
    this.maxExpansions=maxExpansions;
    return this;
  }
  public MultiMatchQueryBuilder minimumShouldMatch(  String minimumShouldMatch){
    this.minimumShouldMatch=minimumShouldMatch;
    return this;
  }
  public MultiMatchQueryBuilder rewrite(  String rewrite){
    this.rewrite=rewrite;
    return this;
  }
  public MultiMatchQueryBuilder fuzzyRewrite(  String fuzzyRewrite){
    this.fuzzyRewrite=fuzzyRewrite;
    return this;
  }
  /** 
 * @deprecated use a tieBreaker of 1.0f to disable "dis-max"query or select the appropriate  {@link Type}
 */
  @Deprecated public MultiMatchQueryBuilder useDisMax(  boolean useDisMax){
    this.useDisMax=useDisMax;
    return this;
  }
  /** 
 * <p>Tie-Breaker for "best-match" disjunction queries (OR-Queries). The tie breaker capability allows documents that match more than one query clause (in this case on more than one field) to be scored better than documents that match only the best of the fields, without confusing this with the better case of two distinct matches in the multiple fields.</p> <p>A tie-breaker value of <tt>1.0</tt> is interpreted as a signal to score queries as "most-match" queries where all matching query clauses are considered for scoring.</p>
 * @see Type
 */
  public MultiMatchQueryBuilder tieBreaker(  float tieBreaker){
    this.tieBreaker=tieBreaker;
    return this;
  }
  /** 
 * Sets whether format based failures will be ignored.
 */
  public MultiMatchQueryBuilder lenient(  boolean lenient){
    this.lenient=lenient;
    return this;
  }
  /** 
 * Set a cutoff value in [0..1] (or absolute number >=1) representing the maximum threshold of a terms document frequency to be considered a low frequency term.
 */
  public MultiMatchQueryBuilder cutoffFrequency(  float cutoff){
    this.cutoffFrequency=cutoff;
    return this;
  }
  public MultiMatchQueryBuilder zeroTermsQuery(  MatchQueryBuilder.ZeroTermsQuery zeroTermsQuery){
    this.zeroTermsQuery=zeroTermsQuery;
    return this;
  }
  /** 
 * Sets the query name for the filter that can be used when searching for matched_filters per hit.
 */
  public MultiMatchQueryBuilder queryName(  String queryName){
    this.queryName=queryName;
    return this;
  }
  @Override public void doXContent(  XContentBuilder builder,  Params params) throws IOException {
    builder.startObject(MultiMatchQueryParser.NAME);
    builder.field("query",text);
    builder.startArray("fields");
    for (    String field : fields) {
      final int keySlot;
      if (fieldsBoosts != null && ((keySlot=fieldsBoosts.indexOf(field)) >= 0)) {
        field+="^" + fieldsBoosts.indexGet(keySlot);
      }
      builder.value(field);
    }
    builder.endArray();
    if (type != null) {
      builder.field("type",type.toString().toLowerCase(Locale.ENGLISH));
    }
    if (operator != null) {
      builder.field("operator",operator.toString());
    }
    if (analyzer != null) {
      builder.field("analyzer",analyzer);
    }
    if (boost != null) {
      builder.field("boost",boost);
    }
    if (slop != null) {
      builder.field("slop",slop);
    }
    if (fuzziness != null) {
      fuzziness.toXContent(builder,params);
    }
    if (prefixLength != null) {
      builder.field("prefix_length",prefixLength);
    }
    if (maxExpansions != null) {
      builder.field("max_expansions",maxExpansions);
    }
    if (minimumShouldMatch != null) {
      builder.field("minimum_should_match",minimumShouldMatch);
    }
    if (rewrite != null) {
      builder.field("rewrite",rewrite);
    }
    if (fuzzyRewrite != null) {
      builder.field("fuzzy_rewrite",fuzzyRewrite);
    }
    if (useDisMax != null) {
      builder.field("use_dis_max",useDisMax);
    }
    if (tieBreaker != null) {
      builder.field("tie_breaker",tieBreaker);
    }
    if (lenient != null) {
      builder.field("lenient",lenient);
    }
    if (cutoffFrequency != null) {
      builder.field("cutoff_frequency",cutoffFrequency);
    }
    if (zeroTermsQuery != null) {
      builder.field("zero_terms_query",zeroTermsQuery.toString());
    }
    if (queryName != null) {
      builder.field("_name",queryName);
    }
    builder.endObject();
  }
  public MultiMatchQueryBuilder(){
  }
}
