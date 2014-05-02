package org.scriptkitty.perl.errorwarn;

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
    public static final String compilerErrorsAndWarnings = "compilerErrorsAndWarnings";

    private static final ResourceBundle bundle = ResourceBundleFactory.getBundle(compilerErrorsAndWarnings);

    private static final CompilerErrorOrWarn UNKNOWN = new CompilerErrorOrWarn();

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

    @XmlElement(name = "classification")
    @XmlElementWrapper(name = "classifications")
    private List<Classification> classifications;

    private CompilerErrorOrWarn()
    {
        super();

        this.classifications = new ArrayList<>(1);
        this.classifications.add(new Classification(ClassificationType.U));
    }

    public static CompilerErrorOrWarn getErrorOrWarning(String line)
    {
        return AbstractErrorOrWarn.get(line, bundle, UNKNOWN);
    }

    public Collection<Classification> getClassifications()
    {
        return classifications;
    }

    public boolean isWarning()
    {
        switch (classifications.get(0).getType())
        {
            case W:
            case D:
            case S:
            {
                return true;
            }
            default:
            {
                return false;
            }
        }
    }

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
