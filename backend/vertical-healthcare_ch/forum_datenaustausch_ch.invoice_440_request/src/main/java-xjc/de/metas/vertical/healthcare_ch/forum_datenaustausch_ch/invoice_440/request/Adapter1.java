
package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.request;

import java.math.BigDecimal;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, BigDecimal>
{


    public BigDecimal unmarshal(String value) {
        return new BigDecimal(value);
    }

    public String marshal(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
