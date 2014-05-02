package org.scriptkitty.perl.errorwarn;

import java.util.ResourceBundle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

import org.scriptkitty.perl.internal.AbstractErrorOrWarn;
import org.scriptkitty.perl.internal.ResourceBundleFactory;


@XmlRootElement(name = "eow")
public class PodErrorOrWarn extends AbstractErrorOrWarn
{
    public static final String podErrorsAndWarnings = "podErrorsAndWarnings";

    private static final ResourceBundle bundle = ResourceBundleFactory.getBundle(podErrorsAndWarnings);

    private static final PodErrorOrWarn UNKNOWN = new PodErrorOrWarn();

    @XmlEnum public enum ClassificationType
    {
        @XmlEnumValue("E")
        E,
        @XmlEnumValue("H")
        H,
        @XmlEnumValue("U")
        U,
        @XmlEnumValue("W")
        W;
    }

    @XmlElement private ClassificationType type;

    private PodErrorOrWarn()
    {
        super();
        this.type = ClassificationType.U;
    }

    public static PodErrorOrWarn getErrorOrWarning(String line)
    {
        return AbstractErrorOrWarn.get(line, bundle, UNKNOWN);
    }

    public ClassificationType getClassification()
    {
        return type;
    }
}
