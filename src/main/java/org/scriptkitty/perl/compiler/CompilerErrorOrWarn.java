package org.scriptkitty.perl.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

import org.scriptkitty.perl.internal.AbstractErrorOrWarn;
import org.scriptkitty.perl.internal.ResourceBundleFactory;


@XmlRootElement(name = "eow")
public class CompilerErrorOrWarn extends AbstractErrorOrWarn
{
    //~ Static fields/initializers

    public static final String compilerErrorsAndWarnings = "compilerErrorsAndWarnings";

    private static final ResourceBundle bundle = ResourceBundleFactory.getBundle(compilerErrorsAndWarnings);

    private static final CompilerErrorOrWarn UNKNOWN = new CompilerErrorOrWarn("", MSG, ClassificationType.U);

    //~ Enums

    @XmlEnum public enum ClassificationType
    {
        @XmlEnumValue("A")
        A,
        @XmlEnumValue("D")
        D,
        @XmlEnumValue("F")
        F,
        @XmlEnumValue("P")
        P,
        @XmlEnumValue("S")
        S,
        @XmlEnumValue("U")
        U,
        @XmlEnumValue("W")
        W,
        @XmlEnumValue("X")
        X
    }

    //~ Instance fields

    @XmlElement(name = "classification")
    @XmlElementWrapper(name = "classifications")
    private List<Classification> classifications;

    //~ Constructors

    private CompilerErrorOrWarn(String regexp, String content, CompilerErrorOrWarn.ClassificationType type)
    {
        super(regexp, content);

        this.classifications = new ArrayList<>(1);
        this.classifications.add(new Classification(type));
    }

    private CompilerErrorOrWarn()
    {
        // required by jaxb
    }

    //~ Methods

    public static CompilerErrorOrWarn getErrorOrWarning(String line)
    {
        return AbstractErrorOrWarn.get(line, bundle, UNKNOWN);
    }

    public Collection<Classification> getClassifications()
    {
        return classifications;
    }

    //~ Inner Classes

    @XmlRootElement(name = "classification")
    public static class Classification
    {
        @XmlElement(name = "type")
        private ClassificationType type;
        @XmlElement(name = "pragma")
        @XmlElementWrapper(name = "pragmas")
        private Collection<String> pragmas;

        private Classification()
        {
            // required by jaxb
        }

        private Classification(ClassificationType type)
        {
            this.type = type;
        }

        public Collection<String> getPragmas()
        {
            return pragmas;
        }

        public ClassificationType getType()
        {
            return type;
        }

    }
}
