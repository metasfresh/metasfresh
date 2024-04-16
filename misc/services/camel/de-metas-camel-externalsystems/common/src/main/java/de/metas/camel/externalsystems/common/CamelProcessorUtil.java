package de.metas.camel.externalsystems.common;

import de.metas.common.util.Check;
import lombok.experimental.UtilityClass;
import org.apache.camel.RuntimeCamelException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@UtilityClass
public class CamelProcessorUtil 
{
    private final NumberFormat GERMAN_NUMBER_FORMAT = NumberFormat.getInstance(Locale.GERMANY);
    
    public BigDecimal parseGermanNumberString(@Nullable final String inputString)
    {
        if (Check.isBlank(inputString))
        {
            return null;
        }
        try
        {
            final Number parse = GERMAN_NUMBER_FORMAT.parse(inputString.trim());
            return new BigDecimal(parse.toString());
        }
        catch (ParseException e)
        {
            throw new RuntimeCamelException("Caught ParseException while trying to parse string '" + inputString + "'", e);
        }
    }
    
}
