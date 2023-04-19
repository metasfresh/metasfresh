package de.metas.process;

import com.google.common.collect.ImmutableMap;
import de.metas.process.NestedParams.ParamMapping;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.RangeAwareParams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NewClassNamingConvention")
public class ProcessClassInfo_NestedParams_Test
{
	public static class NestedParamsHolder
	{
		@Param(parameterName = "Param1") String param1;
	}

	public static class ProcessImpl_WithTwoNestedParams extends JavaProcess
	{
		@NestedParams
		private final NestedParamsHolder nestedParams1 = new NestedParamsHolder();

		@NestedParams({
				@ParamMapping(externalParameterName = "Set2_Param1", internalParameterName = "Param1")
		})
		private final NestedParamsHolder nestedParams2 = new NestedParamsHolder();

		@Override
		public String doIt() {return MSG_OK;}
	}

	private static RangeAwareParams singleParam(String parameterName, Object value)
	{
		final HashMap<String, Object> values = new HashMap<>();
		values.put(parameterName, value);
		final Map<String, Object> valuesTo = ImmutableMap.of();
		return RangeAwareParams.ofMaps(values, valuesTo);
	}

	@BeforeEach
	@AfterEach
	public void resetCache()
	{
		ProcessClassInfo.resetCache();
	}

	@Test
	public void test_ProcessImpl_WithTwoNestedParams()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl_WithTwoNestedParams.class);
		assertThat(processClassInfo.getParameterInfos("Param1")).hasSize(1);
		assertThat(processClassInfo.getParameterInfos("Set2_Param1")).hasSize(1);

		final ProcessImpl_WithTwoNestedParams processInstance = new ProcessImpl_WithTwoNestedParams();

		assertThat(processInstance.nestedParams1.param1).isNull();
		assertThat(processInstance.nestedParams2.param1).isNull();

		CollectionUtils.singleElement(processClassInfo.getParameterInfos("Param1"))
				.loadParameterValue(processInstance, singleParam("Param1", "test value"), true);

		assertThat(processInstance.nestedParams1.param1).isEqualTo("test value");
		assertThat(processInstance.nestedParams2.param1).isNull();

		CollectionUtils.singleElement(processClassInfo.getParameterInfos("Set2_Param1"))
				.loadParameterValue(processInstance, singleParam("Set2_Param1", "test other value"), true);

		assertThat(processInstance.nestedParams1.param1).isEqualTo("test value");
		assertThat(processInstance.nestedParams2.param1).isEqualTo("test other value");
	}

	//
	//
	// ------------------------------------------------------------------------------------------------------------------
	//
	//

	public static class ProcessImpl_MissingInternalParameter extends JavaProcess
	{
		@NestedParams({
				@ParamMapping(externalParameterName = "RemappedParameterName", internalParameterName = "WrongParameterName")
		})
		private final NestedParamsHolder nestedParams = new NestedParamsHolder();

		@Override
		public String doIt() {return MSG_OK;}
	}

	@Test
	public void test_ProcessImpl_MissingInternalParameter()
	{
		assertThatThrownBy(() -> new ProcessClassInfo(ProcessImpl_MissingInternalParameter.class))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Following internal parameter names are missing while trying to remap nested params")
				.hasMessageEndingWith("[WrongParameterName]");

		assertThat(ProcessClassInfo.of(ProcessImpl_MissingInternalParameter.class)).isSameAs(ProcessClassInfo.NULL);
	}

}
