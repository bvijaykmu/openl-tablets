/* Generated By:JavaCC: Do not edit this line. BExGrammarConstants.java */
package org.openl.grammars.bexgrammar;

public interface BExGrammarConstants {

  int EOF = 0;
  int OR = 6;
  int AND = 7;
  int NOT = 8;
  int ABSTRACT = 9;
  int BREAK = 10;
  int CALCULATE = 11;
  int CASE = 12;
  int CATCH = 13;
  int CONST = 14;
  int CONTINUE = 15;
  int _DEFAULT = 16;
  int DO = 17;
  int ELSE = 18;
  int EXTENDS = 19;
  int FALSE = 20;
  int FINAL = 21;
  int FINALLY = 22;
  int FOR = 23;
  int GOTO = 24;
  int IF = 25;
  int PLUSSTR = 26;
  int IMPLEMENTS = 27;
  int IMPORT = 28;
  int INSTANCEOF = 29;
  int INTERFACE = 30;
  int NATIVE = 31;
  int NEW = 32;
  int NULL = 33;
  int PACKAGE = 34;
  int PRIVATE = 35;
  int PROTECTED = 36;
  int PUBLIC = 37;
  int RETURN = 38;
  int STATIC = 39;
  int SUPER = 40;
  int SWITCH = 41;
  int SYNCHRONIZED = 42;
  int THROW = 43;
  int THROWS = 44;
  int TRANSIENT = 45;
  int TRUE = 46;
  int TRY = 47;
  int VOID = 48;
  int VOLATILE = 49;
  int WHILE = 50;
  int WHERE = 51;
  int LPAREN = 52;
  int RPAREN = 53;
  int LBRACE = 54;
  int RBRACE = 55;
  int LBRACKET = 56;
  int RBRACKET = 57;
  int SEMICOLON = 58;
  int COMMA = 59;
  int DOT = 60;
  int ASSIGN = 61;
  int GT = 62;
  int LT = 63;
  int BANG = 64;
  int TILDE = 65;
  int HOOK = 66;
  int COLON = 67;
  int EQ = 68;
  int LE = 69;
  int GE = 70;
  int NE = 71;
  int BOOL_OR = 72;
  int BOOL_AND = 73;
  int INCR = 74;
  int DECR = 75;
  int PLUS = 76;
  int MINUS = 77;
  int STAR = 78;
  int SLASH = 79;
  int BIT_AND = 80;
  int BIT_OR = 81;
  int BIT_XOR = 82;
  int REM = 83;
  int LSHIFT = 84;
  int RSIGNEDSHIFT = 85;
  int RUNSIGNEDSHIFT = 86;
  int PLUSASSIGN = 87;
  int MINUSASSIGN = 88;
  int STARASSIGN = 89;
  int SLASHASSIGN = 90;
  int ANDASSIGN = 91;
  int ORASSIGN = 92;
  int XORASSIGN = 93;
  int REMASSIGN = 94;
  int LSHIFTASSIGN = 95;
  int RSIGNEDSHIFTASSIGN = 96;
  int RUNSIGNEDSHIFTASSIGN = 97;
  int EXP = 98;
  int IMPL = 99;
  int INTEGER_LITERAL = 100;
  int DECIMAL_LITERAL = 101;
  int HEX_LITERAL = 102;
  int OCTAL_LITERAL = 103;
  int FLOATING_POINT_LITERAL = 104;
  int BUSINESS_INTEGER_LITERAL = 105;
  int PERCENT_LITERAL = 106;
  int EXPONENT = 107;
  int CHARACTER_LITERAL = 108;
  int STRING_LITERAL = 109;
  int IDENTIFIER = 110;
  int LETTER = 111;
  int DIGIT = 112;
  int SINGLE_LINE_COMMENT = 115;
  int FORMAL_COMMENT = 116;
  int MULTI_LINE_COMMENT = 117;

  int DEFAULT = 0;
  int IN_FORMAL_COMMENT = 1;
  int IN_MULTI_LINE_COMMENT = 2;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\r\"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\f\"",
    "\"or\"",
    "\"and\"",
    "\"not\"",
    "\"abstract\"",
    "\"break\"",
    "\"Calculate\"",
    "\"case\"",
    "\"catch\"",
    "\"const\"",
    "\"continue\"",
    "\"default\"",
    "\"do\"",
    "\"else\"",
    "\"extends\"",
    "\"false\"",
    "\"final\"",
    "\"finally\"",
    "\"for\"",
    "\"goto\"",
    "\"if\"",
    "\"plus\"",
    "\"implements\"",
    "\"import\"",
    "\"instanceof\"",
    "\"interface\"",
    "\"native\"",
    "\"new\"",
    "\"null\"",
    "\"package\"",
    "\"private\"",
    "\"protected\"",
    "\"public\"",
    "\"return\"",
    "\"static\"",
    "\"super\"",
    "\"switch\"",
    "\"synchronized\"",
    "\"throw\"",
    "\"throws\"",
    "\"transient\"",
    "\"true\"",
    "\"try\"",
    "\"void\"",
    "\"volatile\"",
    "\"while\"",
    "\"where\"",
    "\"(\"",
    "\")\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\";\"",
    "\",\"",
    "\".\"",
    "\"=\"",
    "\">\"",
    "\"<\"",
    "\"!\"",
    "\"~\"",
    "\"?\"",
    "\":\"",
    "\"==\"",
    "\"<=\"",
    "\">=\"",
    "\"!=\"",
    "\"||\"",
    "\"&&\"",
    "\"++\"",
    "\"--\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"&\"",
    "\"|\"",
    "\"^\"",
    "\"%\"",
    "\"<<\"",
    "\">>\"",
    "\">>>\"",
    "\"+=\"",
    "\"-=\"",
    "\"*=\"",
    "\"/=\"",
    "\"&=\"",
    "\"|=\"",
    "\"^=\"",
    "\"%=\"",
    "\"<<=\"",
    "\">>=\"",
    "\">>>=\"",
    "\"**\"",
    "\"->\"",
    "<INTEGER_LITERAL>",
    "<DECIMAL_LITERAL>",
    "<HEX_LITERAL>",
    "<OCTAL_LITERAL>",
    "<FLOATING_POINT_LITERAL>",
    "<BUSINESS_INTEGER_LITERAL>",
    "<PERCENT_LITERAL>",
    "<EXPONENT>",
    "<CHARACTER_LITERAL>",
    "<STRING_LITERAL>",
    "<IDENTIFIER>",
    "<LETTER>",
    "<DIGIT>",
    "<token of kind 113>",
    "\"/*\"",
    "<SINGLE_LINE_COMMENT>",
    "\"*/\"",
    "\"*/\"",
    "<token of kind 118>",
    "\"of the\"",
    "\"is less than\"",
    "\"is more than\"",
    "\"is less or equal\"",
    "\"is no more than\"",
    "\"is in\"",
    "\"is more or equal\"",
    "\"is no less than\"",
  };

}
