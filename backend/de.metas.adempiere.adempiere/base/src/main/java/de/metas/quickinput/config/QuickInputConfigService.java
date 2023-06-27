package de.metas.quickinput.config;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class QuickInputConfigService
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public Optional<QuickInputConfigLayout> getLayoutBySysconfig(@NonNull final String sysconfigName)
	{
		final String layoutString = StringUtils.trimBlankToNull(sysConfigBL.getValue(sysconfigName));

		try
		{
			return parseLayoutFromSysconfigValue(layoutString);
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("sysconfigName", sysconfigName)
					.setParameter("layoutString", layoutString);
		}
	}

	public static Optional<QuickInputConfigLayout> parseLayoutFromSysconfigValue(@Nullable final String layoutStringParam)
	{
		final String layoutStringNorm = StringUtils.trimBlankToNull(layoutStringParam);

		if (layoutStringNorm == null || "-".equals(layoutStringNorm))
		{
			return Optional.empty();
		}

		final ArrayList<QuickInputConfigLayout.Field> fields = new ArrayList<>();
		for (final String fieldLayoutString : Splitter.on(",").splitToList(layoutStringNorm))
		{
			final String fieldLayoutStringNorm = StringUtils.trimBlankToNull(fieldLayoutString);
			if (fieldLayoutStringNorm == null)
			{
				throw new AdempiereException("Empty field name not allowed");
			}

			final String fieldName;
			final boolean mandatory;
			if (fieldLayoutStringNorm.endsWith("?"))
			{
				mandatory = false;
				fieldName = fieldLayoutStringNorm.substring(0, fieldLayoutStringNorm.length() - 1);
			}
			else
			{
				mandatory = true;
				fieldName = fieldLayoutStringNorm;
			}

			if (fieldName.isEmpty())
			{
				throw new AdempiereException("Empty field name not allowed");
			}

			fields.add(QuickInputConfigLayout.Field.builder()
					.fieldName(fieldName)
					.mandatory(mandatory)
					.build());
		}

		if (fields.isEmpty())
		{
			throw new AdempiereException("No fields");
		}

		return Optional.of(
				QuickInputConfigLayout.builder()
						.fields(ImmutableList.copyOf(fields))
						.build());
	}
}
