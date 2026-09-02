package de.metas.ui.web.window.health;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

@Value
@EqualsAndHashCode(exclude = "_json")
class ContextPath implements Comparable<ContextPath>
{
	private static final Splitter SPLITTER = Splitter.on('.').trimResults().omitEmptyStrings();

	@NonNull ImmutableList<ContextPathElement> elements;

	@Nullable @NonFinal transient String _json;

	private ContextPath(@NonNull final ImmutableList<ContextPathElement> elements)
	{
		this.elements = Check.assumeNotEmpty(elements, "elements is not empty");
	}

	@JsonCreator
	public static ContextPath ofJson(@NonNull String json)
	{
		return new ContextPath(
				SPLITTER.splitToList(json)
						.stream()
						.map(ContextPathElement::ofJson)
						.collect(ImmutableList.toImmutableList())
		);
	}

	public static ContextPath root(@NonNull final DocumentEntityDescriptor entityDescriptor)
	{

		return new ContextPath(ImmutableList.of(ContextPathElement.ofNameAndId(
				extractName(entityDescriptor),
				entityDescriptor.getWindowId().toInt())
		));
	}

	static String extractName(final @NonNull DocumentEntityDescriptor entityDescriptor)
	{
		final String tableName = entityDescriptor.getTableNameOrNull();
		return tableName != null ? tableName : entityDescriptor.getInternalName();
	}

	@Override
	public String toString() {return toJson();}

	@JsonValue
	public String toJson()
	{
		String json = this._json;
		if (json == null)
		{
			json = this._json = computeJson();
		}
		return json;
	}

	private String computeJson()
	{
		return elements.stream()
				.map(ContextPathElement::toJson)
				.collect(Collectors.joining("."));
	}

	public ContextPath newChild(@NonNull final String name)
	{
		return newChild(ContextPathElement.ofName(name));
	}

	public ContextPath newChild(@NonNull final DocumentEntityDescriptor entityDescriptor)
	{
		return newChild(ContextPathElement.ofNameAndId(
				extractName(entityDescriptor),
				AdTabId.toRepoId(entityDescriptor.getAdTabIdOrNull())
		));
	}

	private ContextPath newChild(@NonNull final ContextPathElement element)
	{
		return new ContextPath(ImmutableList.<ContextPathElement>builder()
				.addAll(elements)
				.add(element)
				.build());
	}

	public AdWindowId getAdWindowId()
	{
		return AdWindowId.ofRepoId(elements.get(0).getId());
	}

	@Override
	public int compareTo(@NonNull final ContextPath other)
	{
		return toJson().compareTo(other.toJson());
	}
}

@Value
class ContextPathElement
{
	@NonNull String name;
	int id;

	@JsonCreator
	public static ContextPathElement ofJson(@NonNull final String json)
	{
		try
		{
			final int idx = json.indexOf("/");
			if (idx > 0)
			{
				String name = json.substring(0, idx);
				int id = Integer.parseInt(json.substring(idx + 1));
				return new ContextPathElement(name, id);
			}
			else
			{
				return new ContextPathElement(json, -1);
			}
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed parsing: " + json, ex);
		}
	}

	public static ContextPathElement ofName(@NonNull final String name) {return new ContextPathElement(name, -1);}

	public static ContextPathElement ofNameAndId(@NonNull final String name, final int id) {return new ContextPathElement(name, id > 0 ? id : -1);}

	@Override
	public String toString() {return toJson();}

	@JsonValue
	public String toJson() {return id > 0 ? name + "/" + id : name;}
}
