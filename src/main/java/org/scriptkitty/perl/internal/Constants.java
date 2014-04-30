package org.scriptkitty.perl.internal;

public final class Constants
{
    public static final char AMP_C = '&';
    public static final String AMP_S = "&";

    public static final char ARRAY_C = '@';
    public static final String ARRAY_S = "@";

    public static final String BASE = "base";
    public static final String CONTINUE = "continue";

    public static final String DATA_SEP = "__DATA__";

    public static final String DOLLAR_POUND = "$#";

    public static final String DOUBLE_COLON = "::";

    public static final String ELSE = "else";
    public static final String ELSIF = "elsif";
    public static final String END_SEP = "__END__";
    public static final String FIELDS = "fields";

    public static final String FOR = "for";
    public static final String FOREACH = "foreach";

    public static final char HASH_C = '%';
    public static final String HASH_S = "%";

    public static final String IF = "if";
    public static final String NO = "no";
    public static final String PACKAGE = "package";
    public static final String PARENT = "parent";

    public static final char SCALAR_C = '$';
    public static final String SCALAR_S = "$";

    public static final String STRICT = "strict";
    public static final String SUB = "sub";
    public static final String UNLESS = "unless";
    public static final String UNTIL = "until";
    public static final String USE = "use";
    public static final String V6 = "v6";
    public static final String WHILE = "while";

    public static final String[] CONTROL = { "redo", "next", "last", "return", "goto" };

    public static final String[] COMPOUND = { IF, UNLESS, FOR, FOREACH, UNTIL, WHILE };

    public static final String[] CONDITIONAL = { IF, ELSIF, UNLESS, UNTIL, WHILE };

    public static final String[] LOOP = { FOR, FOREACH, UNTIL, WHILE };

    public static final String[] SCHEDULED = { "BEGIN", "CHECK", "INIT", "END", "UNITCHECK" };

    public static final String[] VARIABLE = { "my", "local", "our", "state" };
}
