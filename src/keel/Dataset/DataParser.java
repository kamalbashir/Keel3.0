/* Generated By:JavaCC: Do not edit this line. DataParser.java */
package keel.Dataset;
import java.io.*;
import java.util.*;
import java.lang.*;

public class DataParser implements DataParserConstants {

    /**
     * Static variable of type dataParser 
     */
    static DataParser dataParser= null;


    /**
     * String where the relation name will be stored
     */
    static String relationName = null;

    /**
     * This flag indicates if is a train run
     */
    static boolean isTrain = false;

    /**
     * In this vector, all the input attribute names are stored 
     */
    static Vector inputAttrNames = new Vector();

    /**
     * In this vector, all the output attribute names are stored 
     */
    static Vector outputAttrNames = new Vector();
    static Vector inputTestAttrNames = new Vector();
    static Vector outputTestAttrNames = new Vector();
    static boolean inputsDef = false;
    static boolean outputsDef = false;

/** 
 * It instances a new instance of the class
 */
  DataParser() {}


/**
  * It's the parser main method. It opens the file specified and parses it.
  * The name of the file to be parsed has to be received as a parameter. 
  * @param fileName is the name of the file to be parsed.
  * @param isTrain indicates if it's a train run.
  */
  public static void headerParse(String fileName, boolean _isTrain) throws HeaderFormatException{
    try {
        FileInputStream f = null;
        isTrain = _isTrain;
        inputTestAttrNames  = new Vector();
        outputTestAttrNames = new Vector();
        inputsDef = false;
        outputsDef = false;
        try {
            f = new FileInputStream(fileName);
        }catch (FileNotFoundException e){
            System.err.println ("The data input file '"+fileName+"' doesn't exist.");
            System.exit(0);
        }

        if (dataParser == null){
            dataParser = new DataParser(f);
        }

        // Call the initial symbol
        dataParser.ReInit(f);  // We have to reInit the parser.

        dataParser.ppal();

        // Closing the input file
        f.close();
    }catch(ParseException e){
        Token t = getToken(1);
        throw new HeaderFormatException (("SINTACTICAL ERROR. It has been found the token \""+t.image+"\" at line "+t.beginLine+" column "+t.beginColumn+"."));
    } catch(Exception e) {
        // If any exception is thrown, it's printed
        e.printStackTrace(System.err);
    }
  }

//// PRODUCTIONS OF OUR GRAMAR




///////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// INITIAL PRODUCTION OF THE GRAMMAR /////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  static final public void ppal() throws ParseException {
                 Token t=null;
    readAttributes(0);
    readInputs();
    readOutputs();
    jj_consume_token(DATA);
         processInputsAndOutputs();
  }

   //end ppal


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  static final public void readRelation() throws ParseException {
    jj_consume_token(RELATION);
    jj_consume_token(IDENT);
                                 DataParser.relationName = getToken(0).image.trim();
                         Attributes.setRelationName (DataParser.relationName);
    identList();
  }

   //end readRelation
  static final public void identList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENT:
      jj_consume_token(IDENT);
      break;
    default:
      jj_la1[0] = jj_gen;

    }
  }

   //end identList


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  static final public void readAttributes(int attCount) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ATTRIBUTE:
      jj_consume_token(ATTRIBUTE);
      jj_consume_token(IDENT);
                         //A new attribute is instanced
                         Attribute at = new Attribute();
                         at.setName(getToken(0).image.trim());
      attributeType(at);
                         if (isTrain){
                            Attributes.addAttribute(at);
                        }
                        else if (!Attributes.getAttribute(attCount).equals(at)){
                            Token t = getToken(0);
                            ErrorInfo er = new ErrorInfo(ErrorInfo.AttributeNotDefinedInTrain, 0, t.beginLine, attCount, 0, isTrain,
                                                        ("Attribute "+at.getName()+" read in test has not been defined in train DB"));
                            InstanceSet.errorLogger.setError(er);
                        }
                        attCount++;
      readAttributes(attCount);
      break;
    default:
      jj_la1[1] = jj_gen;

    }
  }

    //end readAttributes
  static final public void attributeType(Attribute at) throws ParseException {
                                      Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEG:
      jj_consume_token(INTEG);
                 //The type of the attribute is set to INTEGER
                  at.setType(Attribute.INTEGER);
      integerBoundaries(at);
      break;
    case REAL:
      jj_consume_token(REAL);
                 //The type of the attribute is set to REAL
                 at.setType(Attribute.REAL);
      realBoundaries(at);
      break;
    case CLOPENED:
      jj_consume_token(CLOPENED);
                    //The type of the attribute is set to NOMINAL
                    at.setType(Attribute.NOMINAL);
      identNum();
                            at.addNominalValue(getToken(0).image.trim());
                            //And setting fixedBounds to true
                            at.setFixedBounds(true);
      nominalList(at);
      jj_consume_token(CLCLOSED);
      break;
    default:
      jj_la1[2] = jj_gen;

            at.setType(Attribute.NOMINAL);
    }
  }

   //end attributeType
  static final public void integerBoundaries(Attribute at) throws ParseException {
                                          int min, max;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COPENED:
      jj_consume_token(COPENED);
      jj_consume_token(INT_CONST);
                             min = Integer.parseInt(getToken(0).image.trim());
      jj_consume_token(COLON);
      jj_consume_token(INT_CONST);
                             max = Integer.parseInt(getToken(0).image.trim());
                             //setBounds set "fixedBound" to true.
                             at.setBounds(min,max);
      jj_consume_token(CCLOSED);
      break;
    default:
      jj_la1[3] = jj_gen;

    }
  }

  static final public void realBoundaries(Attribute at) throws ParseException {
                                       double min, max;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COPENED:
      jj_consume_token(COPENED);
      realConst();
                             min = Double.parseDouble(getToken(0).image.trim());
      jj_consume_token(COLON);
      realConst();
                             max = Double.parseDouble(getToken(0).image.trim());
                             //setBounds set "fixedBound" to true.
                             at.setBounds(min,max);
      jj_consume_token(CCLOSED);
      break;
    default:
      jj_la1[4] = jj_gen;

    }
  }

  static final public void nominalList(Attribute att) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COLON:
      jj_consume_token(COLON);
      identNum();
                            att.addNominalValue(getToken(0).image.trim());
      nominalList(att);
      break;
    default:
      jj_la1[5] = jj_gen;

    }
  }

    //end nominalList


/////////////////////////////////////////////////////////////////////////////////////////////////////////
  static final public void realConst() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case REAL_CONST:
      jj_consume_token(REAL_CONST);
      exponent();
      break;
    case INT_CONST:
      jj_consume_token(INT_CONST);
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

    //end realConst
  static final public void exponent() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EXP:
      jj_consume_token(EXP);
      jj_consume_token(INT_CONST);
      break;
    default:
      jj_la1[7] = jj_gen;

    }
  }

  static final public void identNum() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENT:
      jj_consume_token(IDENT);
      break;
    case INT_CONST:
      jj_consume_token(INT_CONST);
      break;
    case REAL_CONST:
      jj_consume_token(REAL_CONST);
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

    //end identNum


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  static final public void readInputs() throws ParseException {
                      String attName=null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INPUTS:
      jj_consume_token(INPUTS);
      jj_consume_token(IDENT);
                         attName = getToken(0).image.trim();
                         inputsDef = true;
                         if (isTrain){
                            if (Attributes.getAttribute(attName) == null){
                                Token t = getToken(0);
                                ErrorInfo er = new ErrorInfo(ErrorInfo.InputTrainAttributeNotDefined, 0, t.beginLine, 0, 0, isTrain,
                                        ("The attribute "+attName+" defined in @inputs has not been defined previously. It will be ignored."));
                                InstanceSet.errorLogger.setError(er);
                            }
                            else inputAttrNames.add(attName);
                        }
                        else{
                            if (!inputAttrNames.contains(attName)){
                                Token t = getToken(0);
                                ErrorInfo er = new ErrorInfo(ErrorInfo.InputTestAttributeNotDefined, 0, t.beginLine, 0, 0, isTrain,
                                        ("The attribute "+attName+" defined in @inputs in test, it has not been defined in @inputs in its train dataset. It will be ignored"));
                                InstanceSet.errorLogger.setError(er);
                            }
                            inputTestAttrNames.add(attName);
                        }
      inputs_list();
      break;
    default:
      jj_la1[9] = jj_gen;

    }
  }

  static final public void inputs_list() throws ParseException {
                        String attName=null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COLON:
      jj_consume_token(COLON);
      jj_consume_token(IDENT);
                        attName = getToken(0).image.trim();
                        if (isTrain){
                            if (Attributes.getAttribute(attName) == null){
                                Token t = getToken(0);
                                ErrorInfo er = new ErrorInfo(ErrorInfo.InputTrainAttributeNotDefined, 0, t.beginLine, 0, 0, isTrain,
                                        ("The attribute "+attName+" defined in @inputs has not been defined previously. It will be ignored."));
                                InstanceSet.errorLogger.setError(er);
                            }
                            else inputAttrNames.add(attName);
                        }
                        else{
                             if (!inputAttrNames.contains(attName)){
                                Token t = getToken(0);
                                ErrorInfo er = new ErrorInfo(ErrorInfo.InputTestAttributeNotDefined, 0, t.beginLine, 0, 0, isTrain,
                                        ("The attribute "+attName+" defined in @inputs in test, it has not been defined in @inputs in its train dataset. It will be ignored."));
                                InstanceSet.errorLogger.setError(er);
                            }
                            inputTestAttrNames.add(attName);
                        }
      inputs_list();
      break;
    default:
      jj_la1[10] = jj_gen;

    }
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  static final public void readOutputs() throws ParseException {
                        String attName=null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OUTPUTS:
      jj_consume_token(OUTPUTS);
      jj_consume_token(IDENT);
                          attName = getToken(0).image.trim();
                          outputsDef = true;
                          if (isTrain){
                            if (Attributes.getAttribute(attName) == null){
                                Token t = getToken(0);
                                ErrorInfo er = new ErrorInfo(ErrorInfo.OutputTrainAttributeNotDefined, 0, t.beginLine, 0, 0, isTrain,
                                        ("The attribute "+attName+" defined in @outputs has not been defined previously. It will be ignored."));
                                InstanceSet.errorLogger.setError(er);
                            }
                            else outputAttrNames.add(attName);
                          }
                          else{
                            if (!outputAttrNames.contains(attName)){
                                Token t = getToken(0);
                                ErrorInfo er = new ErrorInfo(ErrorInfo.OutputTestAttributeNotDefined, 0, t.beginLine, 0, 0, isTrain,
                                        ("The attribute "+attName+" defined in @outputs in test, it has not been defined in @outputs in its train dataset. It will be ignored."));
                                InstanceSet.errorLogger.setError(er);
                            }
                            outputTestAttrNames.add(attName);
                          }
      outputs_list();
      break;
    default:
      jj_la1[11] = jj_gen;

    }
  }

  static final public void outputs_list() throws ParseException {
                         String attName=null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COLON:
      jj_consume_token(COLON);
      jj_consume_token(IDENT);
                         attName = getToken(0).image.trim();
                         if (isTrain){
                            if (Attributes.getAttribute(attName) == null){
                                Token t = getToken(0);
                                ErrorInfo er = new ErrorInfo(ErrorInfo.OutputTrainAttributeNotDefined, 0, t.beginLine, 0, 0, isTrain,
                                        ("The attribute "+attName+" defined in @outputs has not been defined previously. It will be ignored."));
                                InstanceSet.errorLogger.setError(er);
                            }
                            else outputAttrNames.add(attName);
                         }
                         else{
                            if (!outputAttrNames.contains(attName)){
                                Token t = getToken(0);
                                ErrorInfo er = new ErrorInfo(ErrorInfo.OutputTestAttributeNotDefined, 0, t.beginLine, 0, 0, isTrain,
                                        ("The attribute "+attName+" defined in @outputs in test, it has not been defined in @outputs in its train dataset. It will be ignored."));
                                InstanceSet.errorLogger.setError(er);
                            }
                            outputTestAttrNames.add(attName);
                         }
      outputs_list();
      break;
    default:
      jj_la1[12] = jj_gen;

    }
  }

  static void processInputsAndOutputs() throws ParseException {
  //Afteer parsing the header, the inputs and the outputs are prepared.
    if (DataParser.isTrain){
        if (!inputsDef && !outputsDef){
            outputAttrNames.add(Attributes.getAttribute(Attributes.getNumAttributes()-1).getName());
            inputAttrNames = Attributes.getAttributesExcept(outputAttrNames);
        }else if (!inputsDef && outputsDef){
            inputAttrNames = Attributes.getAttributesExcept(outputAttrNames);
        }else if (inputsDef && !outputsDef){
            outputAttrNames = Attributes.getAttributesExcept(inputAttrNames);
        }

        Attributes.setOutputInputAttributes(inputAttrNames, outputAttrNames);
    }
    else{ //Checking is inputs and outputs (if defined), respect the train definition
        if (inputsDef){
            if (!Attributes.areAllDefinedAsInputs(inputTestAttrNames)){
                ErrorInfo er = new ErrorInfo(ErrorInfo.InputsInTestNotEquals, 0, 0, 0, 0, isTrain,
                                        ("The @input attributes definition of test run doesn't match with the train definition"));
                InstanceSet.errorLogger.setError(er);

            }
        }
        if (outputsDef){
            if (!Attributes.areAllDefinedAsOutputs(outputTestAttrNames)){
                ErrorInfo er = new ErrorInfo(ErrorInfo.OutputsInTestNotEquals, 0, 0, 0, 0, isTrain,
                                        ("The @output attributes definition of test run doesn't match with the train definition"));
                InstanceSet.errorLogger.setError(er);
            }
        }
    }
  }

  static private boolean jj_initialized_once = false;
  static public DataParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  static public Token token, jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[13];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x8000000,0x200,0x46000,0x10000,0x10000,0x100000,0x2800000,0x8000,0xa800000,0x400,0x100000,0x800,0x100000,};
   }

  public DataParser(java.io.InputStream stream) {
     this(stream, null);
  }
  public DataParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new DataParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  public DataParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new DataParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  public DataParser(DataParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  public void ReInit(DataParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  static final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.Vector jj_expentries = new java.util.Vector();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  static public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[31];
    for (int i = 0; i < 31; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 13; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 31; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  static final public void enable_tracing() {
  }

  static final public void disable_tracing() {
  }

   //end main

}
