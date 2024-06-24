package de.metas.process;

import de.metas.util.Check;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * Immutable Process Parameter
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2430845 ] Add ProcessInfoParameter.getParameterAsBoolean method
 * @version $Id: ProcessInfoParameter.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 */
public final class ProcessInfoParameter implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 4536416337960754407L;

	public static ProcessInfoParameter of(final String parameterName, final int parameterValue)
	{
		final Integer parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public static ProcessInfoParameter of(@NonNull final String parameterName, @Nullable final RepoIdAware id)
	{
		final Integer parameterValue = id != null ? id.getRepoId() : null;
		final Integer parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public static ProcessInfoParameter of(final String parameterName, final String parameterValue)
	{
		final String parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public static ProcessInfoParameter of(final String parameterName, final BigDecimal parameterValue)
	{
		final BigDecimal parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public static ProcessInfoParameter of(final String parameterName, final java.util.Date parameterValue)
	{
		final java.util.Date parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public static ProcessInfoParameter of(final String parameterName, final java.util.Date parameterValue, final java.util.Date parameterValueTo)
	{
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public static ProcessInfoParameter of(final String parameterName, final boolean parameterValue)
	{
		final Boolean parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public static ProcessInfoParameter ofValueObject(final String parameterName, final Object parameterValue)
	{
		final Object parameterValueTo = null;
		final String info = null;
		final String info_To = null;
		return new ProcessInfoParameter(parameterName, parameterValue, parameterValueTo, info, info_To);
	}

	public ProcessInfoParameter(
			final String parameterName,
			@Nullable final Object parameter,
			@Nullable final Object parameter_To,
			@Nullable final String info,
			@Nullable final String info_To)
	{
		m_ParameterName = parameterName;
		m_Parameter = parameter;
		m_Parameter_To = parameter_To;
		m_Info = info == null ? "" : info;
		m_Info_To = info_To == null ? "" : info_To;
	}

	private final String m_ParameterName;
	@Nullable private final Object m_Parameter;
	@Nullable private final Object m_Parameter_To;
	@Nullable private final String m_Info;
	@Nullable private final String m_Info_To;

	@Override
	public String toString()
	{
		// From .. To
		if (m_Parameter_To != null || !Check.isEmpty(m_Info_To))
		{
			return "ProcessInfoParameter[" + m_ParameterName + "=" + m_Parameter
					+ (m_Parameter == null ? "" : "{" + m_Parameter.getClass().getName() + "}")
					+ " (" + m_Info + ") - "
					+ m_Parameter_To
					+ (m_Parameter_To == null ? "" : "{" + m_Parameter_To.getClass().getName() + "}")
					+ " (" + m_Info_To + ")";
		}
		// Value
		else
		{
			return "ProcessInfoParameter[" + m_ParameterName + "=" + m_Parameter
					+ (m_Parameter == null ? "" : "{" + m_Parameter.getClass().getName() + "}")
					+ " (" + m_Info + ")";
		}
	}

	public String getParameterName()
	{
		return m_ParameterName;
	}

	@Nullable
	public String getInfo()
	{
		return m_Info;
	}

	@Nullable
	public String getInfo_To()
	{
		return m_Info_To;
	}

	@Nullable
	public Object getParameter()
	{
		return m_Parameter;
	}

	@Nullable
	public Object getParameter_To()
	{
		return m_Parameter_To;
	}

	@Nullable
	public String getParameterAsString()
	{
		return toString(m_Parameter);
	}

	@Nullable
	public String getParameter_ToAsString()
	{
		return toString(m_Parameter_To);
	}

	@Nullable
	private static String toString(@Nullable final Object value)
	{
		if (value == null)
		{
			return null;
		}
		return value.toString();
	}

	public int getParameterAsInt()
	{
		return getParameterAsInt(0);
	}

	public int getParameterAsInt(final int defaultValueWhenNull)
	{
		return toInt(m_Parameter, defaultValueWhenNull);
	}

	public <T extends RepoIdAware> T getParameterAsRepoId(@NonNull final Function<Integer, T> mapper)
	{
		return mapper.apply(getParameterAsInt(-1));
	}

	@Nullable
	public <T extends RepoIdAware> T getParameterAsRepoId(@NonNull final Class<T> type)
	{
		return RepoIdAwares.ofRepoIdOrNull(getParameterAsInt(-1), type);
	}

	public int getParameter_ToAsInt()
	{
		return getParameter_ToAsInt(0);
	}

	public int getParameter_ToAsInt(final int defaultValueWhenNull)
	{
		return toInt(m_Parameter_To, defaultValueWhenNull);
	}

	private static int toInt(@Nullable final Object value, final int defaultValueWhenNull)
	{
		if (value == null)
		{
			return defaultValueWhenNull;
		}
		else if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else if (value instanceof RepoIdAware)
		{
			return ((RepoIdAware)value).getRepoId();
		}
		else
		{
			final BigDecimal bd = new BigDecimal(value.toString());
			return bd.intValue();
		}
	}

	public boolean getParameterAsBoolean()
	{
		return StringUtils.toBoolean(m_Parameter);
	}

	@Nullable
	public Boolean getParameterAsBooleanOrNull()
	{
		return StringUtils.toBoolean(m_Parameter, null);
	}

	@Nullable
	public Boolean getParameterAsBoolean(@Nullable Boolean defaultValue)
	{
		return StringUtils.toBoolean(m_Parameter, defaultValue);
	}

	public boolean getParameter_ToAsBoolean()
	{
		return StringUtils.toBoolean(m_Parameter_To);
	}

	@Nullable
	public Timestamp getParameterAsTimestamp()
	{
		return TimeUtil.asTimestamp(m_Parameter);
	}

	@Nullable
	public Timestamp getParameter_ToAsTimestamp()
	{
		return TimeUtil.asTimestamp(m_Parameter_To);
	}

	@Nullable
	public LocalDate getParameterAsLocalDate()
	{
		return TimeUtil.asLocalDate(m_Parameter);
	}

	@Nullable
	public LocalDate getParameter_ToAsLocalDate()
	{
		return TimeUtil.asLocalDate(m_Parameter_To);
	}

	@Nullable
	public ZonedDateTime getParameterAsZonedDateTime()
	{
		return TimeUtil.asZonedDateTime(m_Parameter);
	}

	@Nullable
	public Instant getParameterAsInstant()
	{
		return TimeUtil.asInstant(m_Parameter);
	}

	@Nullable
	public ZonedDateTime getParameter_ToAsZonedDateTime()
	{
		return TimeUtil.asZonedDateTime(m_Parameter_To);
	}

	@Nullable
	public BigDecimal getParameterAsBigDecimal()
	{
		return toBigDecimal(m_Parameter);
	}

	@Nullable
	public BigDecimal getParameter_ToAsBigDecimal()
	{
		return toBigDecimal(m_Parameter_To);
	}

	@Nullable
	private static BigDecimal toBigDecimal(@Nullable final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof BigDecimal)
		{
			return (BigDecimal)value;
		}
		else if (value instanceof Integer)
		{
			return BigDecimal.valueOf((Integer)value);
		}
		else
		{
			return new BigDecimal(value.toString());
		}
	}
}   // ProcessInfoParameter
