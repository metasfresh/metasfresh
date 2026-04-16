package de.metas.handlingunits.attribute.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class HUAttributesBLTest
{
	private static final HuId HU_1 = HuId.ofRepoId(1);
	private static final HuId HU_2 = HuId.ofRepoId(2);

	private IHUAttributesDAO huAttributesDAO;
	private IAttributeDAO attributeDAO;
	private HUAttributesBL huAttributesBL;

	@BeforeEach
	void setup()
	{
		AdempiereTestHelper.get().init();

		huAttributesDAO = Mockito.mock(IHUAttributesDAO.class);
		attributeDAO = Mockito.mock(IAttributeDAO.class);

		Services.registerService(IHUAttributesDAO.class, huAttributesDAO);
		Services.registerService(IAttributeDAO.class, attributeDAO);

		huAttributesBL = new HUAttributesBL();
	}

	@Test
	void emptyHuIds_returnsEmptyAttributeSet()
	{
		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of());

		assertThat(result.isEmpty()).isTrue();
		Mockito.verifyNoInteractions(huAttributesDAO, attributeDAO);
	}

	@Test
	void allHusShareCommonStringValue_attributeIncluded()
	{
		final AttributeId attrId = AttributeId.ofRepoId(10);
		final I_M_Attribute attr = newAttribute(attrId, "LotNo", "S", true);

		final I_M_HU_Attribute huAttr1 = newStringHUAttribute(HU_1, attrId, "LOT-A");
		final I_M_HU_Attribute huAttr2 = newStringHUAttribute(HU_2, attrId, "LOT-A");

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1, HU_2)))
				.thenReturn(ImmutableList.of(huAttr1, huAttr2));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(attr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1, HU_2));

		assertThat(result.hasAttribute(attrId)).isTrue();
		assertThat(result.getValue(attrId)).isEqualTo("LOT-A");
	}

	@Test
	void husHaveDivergentStringValues_attributeExcluded()
	{
		final AttributeId attrId = AttributeId.ofRepoId(10);
		final I_M_Attribute attr = newAttribute(attrId, "LotNo", "S", true);

		final I_M_HU_Attribute huAttr1 = newStringHUAttribute(HU_1, attrId, "LOT-A");
		final I_M_HU_Attribute huAttr2 = newStringHUAttribute(HU_2, attrId, "LOT-B");

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1, HU_2)))
				.thenReturn(ImmutableList.of(huAttr1, huAttr2));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(attr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1, HU_2));

		assertThat(result.hasAttribute(attrId)).isFalse();
	}

	@Test
	void allHusShareCommonListValue_attributeIncluded()
	{
		final AttributeId attrId = AttributeId.ofRepoId(11);
		final I_M_Attribute attr = newAttribute(attrId, "CountryMadeIn", "L", true);

		final I_M_HU_Attribute huAttr1 = newStringHUAttribute(HU_1, attrId, "DE");
		final I_M_HU_Attribute huAttr2 = newStringHUAttribute(HU_2, attrId, "DE");

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1, HU_2)))
				.thenReturn(ImmutableList.of(huAttr1, huAttr2));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(attr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1, HU_2));

		assertThat(result.hasAttribute(attrId)).isTrue();
		assertThat(result.getValue(attrId)).isEqualTo("DE");
	}

	@Test
	void allHusShareCommonNumberValue_attributeIncluded()
	{
		final AttributeId attrId = AttributeId.ofRepoId(12);
		final I_M_Attribute attr = newAttribute(attrId, "Weight", "N", true);

		final I_M_HU_Attribute huAttr1 = newNumberHUAttribute(HU_1, attrId, new BigDecimal("12.5"));
		final I_M_HU_Attribute huAttr2 = newNumberHUAttribute(HU_2, attrId, new BigDecimal("12.5"));

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1, HU_2)))
				.thenReturn(ImmutableList.of(huAttr1, huAttr2));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(attr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1, HU_2));

		assertThat(result.hasAttribute(attrId)).isTrue();
		assertThat(result.getValueAsBigDecimal(attrId)).isEqualByComparingTo(new BigDecimal("12.5"));
	}

	@Test
	void husHaveDivergentNumberValues_attributeExcluded()
	{
		final AttributeId attrId = AttributeId.ofRepoId(12);
		final I_M_Attribute attr = newAttribute(attrId, "Weight", "N", true);

		final I_M_HU_Attribute huAttr1 = newNumberHUAttribute(HU_1, attrId, new BigDecimal("10.0"));
		final I_M_HU_Attribute huAttr2 = newNumberHUAttribute(HU_2, attrId, new BigDecimal("20.0"));

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1, HU_2)))
				.thenReturn(ImmutableList.of(huAttr1, huAttr2));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(attr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1, HU_2));

		assertThat(result.hasAttribute(attrId)).isFalse();
	}

	@Test
	void allHusShareCommonDateValue_attributeIncluded()
	{
		final AttributeId attrId = AttributeId.ofRepoId(13);
		final I_M_Attribute attr = newAttribute(attrId, "BestBeforeDate", "D", true);

		final Timestamp bestBefore = Timestamp.from(Instant.parse("2026-06-30T00:00:00Z"));
		final I_M_HU_Attribute huAttr1 = newDateHUAttribute(HU_1, attrId, bestBefore);
		final I_M_HU_Attribute huAttr2 = newDateHUAttribute(HU_2, attrId, bestBefore);

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1, HU_2)))
				.thenReturn(ImmutableList.of(huAttr1, huAttr2));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(attr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1, HU_2));

		assertThat(result.hasAttribute(attrId)).isTrue();
		assertThat(result.getValue(attrId)).isEqualTo(bestBefore);
	}

	@Test
	void nonStorageRelevantAttribute_excludedEvenIfCommonValue()
	{
		final AttributeId attrId = AttributeId.ofRepoId(20);
		final I_M_Attribute attr = newAttribute(attrId, "Remark", "S", false /* NOT storage-relevant */);

		final I_M_HU_Attribute huAttr1 = newStringHUAttribute(HU_1, attrId, "same-value");
		final I_M_HU_Attribute huAttr2 = newStringHUAttribute(HU_2, attrId, "same-value");

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1, HU_2)))
				.thenReturn(ImmutableList.of(huAttr1, huAttr2));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(attr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1, HU_2));

		assertThat(result.isEmpty()).isTrue();
	}

	@Test
	void nullValuesInHUAttributes_treatedAsAbsent()
	{
		// HU_1 has value, HU_2 has null => use HU_1 as the common value
		final AttributeId attrId = AttributeId.ofRepoId(10);
		final I_M_Attribute attr = newAttribute(attrId, "LotNo", "S", true);

		final I_M_HU_Attribute huAttr1 = newStringHUAttribute(HU_1, attrId, "LOT-A");
		final I_M_HU_Attribute huAttr2 = newStringHUAttribute(HU_2, attrId, null);

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1, HU_2)))
				.thenReturn(ImmutableList.of(huAttr1, huAttr2));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(attr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1, HU_2));

		assertThat(result.hasAttribute(attrId)).isTrue();
	}

	@Test
	void allHUsHaveNullAttributeValue_attributeExcluded()
	{
		final AttributeId attrId = AttributeId.ofRepoId(10);
		final I_M_Attribute attr = newAttribute(attrId, "LotNo", "S", true);

		final I_M_HU_Attribute huAttr1 = newStringHUAttribute(HU_1, attrId, null);
		final I_M_HU_Attribute huAttr2 = newStringHUAttribute(HU_2, attrId, null);

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1, HU_2)))
				.thenReturn(ImmutableList.of(huAttr1, huAttr2));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(attr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1, HU_2));

		assertThat(result.hasAttribute(attrId)).isFalse();
	}

	@Test
	void mixedStorageRelevantAndNonRelevant_onlyStorageRelevantIncluded()
	{
		final AttributeId storageAttrId = AttributeId.ofRepoId(10);
		final I_M_Attribute storageAttr = newAttribute(storageAttrId, "LotNo", "S", true);

		final AttributeId nonStorageAttrId = AttributeId.ofRepoId(20);
		final I_M_Attribute nonStorageAttr = newAttribute(nonStorageAttrId, "Remark", "S", false);

		final I_M_HU_Attribute storageHuAttr1 = newStringHUAttribute(HU_1, storageAttrId, "LOT-X");
		final I_M_HU_Attribute storageHuAttr2 = newStringHUAttribute(HU_2, storageAttrId, "LOT-X");
		final I_M_HU_Attribute nonStorageHuAttr1 = newStringHUAttribute(HU_1, nonStorageAttrId, "same");
		final I_M_HU_Attribute nonStorageHuAttr2 = newStringHUAttribute(HU_2, nonStorageAttrId, "same");

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1, HU_2)))
				.thenReturn(ImmutableList.of(storageHuAttr1, storageHuAttr2, nonStorageHuAttr1, nonStorageHuAttr2));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(storageAttr, nonStorageAttr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1, HU_2));

		assertThat(result.hasAttribute(storageAttrId)).isTrue();
		assertThat(result.hasAttribute(nonStorageAttrId)).isFalse();
	}

	@Test
	void singleHu_storageRelevantAttributeWithValue_included()
	{
		final AttributeId attrId = AttributeId.ofRepoId(10);
		final I_M_Attribute attr = newAttribute(attrId, "LotNo", "S", true);

		final I_M_HU_Attribute huAttr = newStringHUAttribute(HU_1, attrId, "SINGLE-LOT");

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1)))
				.thenReturn(ImmutableList.of(huAttr));
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of(attr));

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1));

		assertThat(result.hasAttribute(attrId)).isTrue();
		assertThat(result.getValue(attrId)).isEqualTo("SINGLE-LOT");
	}

	@Test
	void unknownAttributeId_noAttributeRecordFound_skipped()
	{
		final AttributeId attrId = AttributeId.ofRepoId(99);

		final I_M_HU_Attribute huAttr = newStringHUAttribute(HU_1, attrId, "some-value");

		Mockito.when(huAttributesDAO.retrieveAllAttributesNoCache(ImmutableSet.of(HU_1)))
				.thenReturn(ImmutableList.of(huAttr));
		// attributeDAO returns nothing for unknown ID
		Mockito.when(attributeDAO.getAttributeRecordsByIds(Mockito.any()))
				.thenReturn(ImmutableList.of());

		final ImmutableAttributeSet result = huAttributesBL.extractCommonStorageRelevantAttributeSet(ImmutableSet.of(HU_1));

		assertThat(result.isEmpty()).isTrue();
	}

	// ---- helpers ----

	private static I_M_Attribute newAttribute(
			final AttributeId attributeId,
			final String value,
			final String attributeValueType,
			final boolean storageRelevant)
	{
		final I_M_Attribute attr = newInstance(I_M_Attribute.class);
		attr.setM_Attribute_ID(attributeId.getRepoId());
		attr.setValue(value);
		attr.setAttributeValueType(attributeValueType);
		attr.setIsStorageRelevant(storageRelevant);
		saveRecord(attr);
		return attr;
	}

	private static I_M_HU_Attribute newStringHUAttribute(
			final HuId huId,
			final AttributeId attributeId,
			final @Nullable String value)
	{
		final I_M_HU_Attribute huAttr = newInstance(I_M_HU_Attribute.class);
		huAttr.setM_HU_ID(huId.getRepoId());
		huAttr.setM_Attribute_ID(attributeId.getRepoId());
		huAttr.setValue(value);
		saveRecord(huAttr);
		return huAttr;
	}

	private static I_M_HU_Attribute newNumberHUAttribute(
			final HuId huId,
			final AttributeId attributeId,
			final BigDecimal valueNumber)
	{
		final I_M_HU_Attribute huAttr = newInstance(I_M_HU_Attribute.class);
		huAttr.setM_HU_ID(huId.getRepoId());
		huAttr.setM_Attribute_ID(attributeId.getRepoId());
		huAttr.setValueNumber(valueNumber);
		saveRecord(huAttr);
		return huAttr;
	}

	private static I_M_HU_Attribute newDateHUAttribute(
			final HuId huId,
			final AttributeId attributeId,
			final Timestamp valueDate)
	{
		final I_M_HU_Attribute huAttr = newInstance(I_M_HU_Attribute.class);
		huAttr.setM_HU_ID(huId.getRepoId());
		huAttr.setM_Attribute_ID(attributeId.getRepoId());
		huAttr.setValueDate(valueDate);
		saveRecord(huAttr);
		return huAttr;
	}
}
