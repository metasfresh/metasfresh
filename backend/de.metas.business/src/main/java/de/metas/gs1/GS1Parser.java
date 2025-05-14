package de.metas.gs1;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

public class GS1Parser
{
	@Nullable
	public static GS1Elements parseElementsOrNull(@NonNull final String sequence)
	{
		try
		{
			return parseElements(sequence);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	public static GS1Elements parseElements(@NonNull final String sequence)
	{
		final ArrayList<GS1Element> elements = new ArrayList<>();

		final GS1SequenceReader reader = new GS1SequenceReader(sequence);
		while (!(reader.remainingLength() == 0))
		{
			int identifierPosition = reader.getPosition();

			try
			{
				final GS1Element element = readElement(reader);
				elements.add(element);
			}
			catch (Exception e)
			{
				throw AdempiereException.wrapIfNeeded(e)
						.setParameter("sequence", sequence)
						.setParameter("position", identifierPosition);
			}
		}

		return GS1Elements.ofList(elements);
	}

	private static GS1Element readElement(@NonNull final GS1SequenceReader reader)
	{
		final int identifierPosition = reader.getPosition();
		GS1ApplicationIdentifier identifier = null;
		String key = null;
		Object data = null;

		// Standard AIs defined in ApplicationIdentifier
		for (GS1ApplicationIdentifier candidate : GS1ApplicationIdentifier.values())
		{
			if (reader.startsWith(candidate.getKey()))
			{
				identifier = candidate;
				key = reader.read(identifier.getKey().length());
				if (identifier.getFormat() == GS1ApplicationIdentifierFormat.CUSTOM)
				{
					data = reader.readDataFieldInCustomFormat(identifier);
				}
				else
				{
					data = reader.readDataFieldInStandardFormat(identifier);
				}

				break;
			}
		}

		// Support for AIs 703s Number of processor with three-digit ISO country code
		if (key == null && reader.remainingLength() >= 4 && reader.startsWith("703"))
		{
			String start = reader.peek(4);
			if (start.charAt(3) >= '0' && start.charAt(3) <= '9')
			{
				key = reader.read(4);
				data = Arrays.asList(reader.readFixedLengthNumeric(3), reader.readVariableLengthAlphanumeric(1, 27));
			}
		}

		// Support for AIs 710-719 National Healthcare Reimbursement Number (NHRN)
		if (key == null && reader.remainingLength() >= 3 && reader.startsWith("71"))
		{
			String start = reader.peek(3);
			if (start.charAt(2) >= '0' && start.charAt(2) <= '9')
			{
				key = reader.read(3);
				data = reader.readVariableLengthAlphanumeric(1, 20);
			}
		}

		// Support for AIs 91-99 Company internal information
		if (key == null && reader.remainingLength() >= 2 && reader.startsWith("9"))
		{
			String start = reader.peek(2);
			if (start.charAt(1) >= '1' && start.charAt(1) <= '9')
			{
				key = reader.read(2);
				data = reader.readVariableLengthAlphanumeric(1, 30);
			}
		}

		//
		//
		//

		if (key == null)
		{
			throw new AdempiereException("Unrecognized AI at position " + identifierPosition);
		}
		if (data == null)
		{
			throw new AdempiereException("Error parsing data field for AI " + key + " at position " + identifierPosition);
		}

		return GS1Element.builder()
				.identifier(identifier)
				.key(key)
				.value(data)
				.build();
	}
}
