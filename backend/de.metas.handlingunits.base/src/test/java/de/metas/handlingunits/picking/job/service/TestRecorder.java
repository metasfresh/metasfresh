package de.metas.handlingunits.picking.job.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.handlingunits.util.HUTracerInstance;
import lombok.NonNull;
import org.junit.jupiter.api.Disabled;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Disabled
public class TestRecorder
{
	@JsonIgnore
	private final HUTracerInstance huTracer;
	@JsonIgnore
	private final Function<Object, String> toJson;

	@JsonProperty("steps")
	private final Map<String, Object> steps = new LinkedHashMap<>();

	public TestRecorder(
			@NonNull final HUTracerInstance huTracer,
			@NonNull final Function<Object, String> toJson)
	{
		this.huTracer = huTracer;
		this.toJson = toJson;
	}

	public void reportStep(final String name, final Object data)
	{
		final int stepIndex = steps.size() + 1;
		final String stepName = "" + stepIndex + ". " + name;

		steps.put(stepName, data);

		System.out.println("========[ " + stepName + " ]==============================================================");
		System.out.println(toJson.apply(data));
		System.out.println("--------------------------------------------------------------------------------------");
	}

	public void reportStepWithAllHUs(final String name)
	{
		reportStep(name, huTracer.dumpAllHUsToJson());
	}

	public void assertMatchesSnapshot()
	{
		expect(this).toMatchSnapshot();
	}
}
