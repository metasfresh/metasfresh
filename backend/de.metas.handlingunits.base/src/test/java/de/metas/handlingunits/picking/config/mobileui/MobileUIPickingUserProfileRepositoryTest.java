/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.handlingunits.picking.config.mobileui;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import de.metas.picking.model.I_PickingProfile_Filter;
import de.metas.picking.model.X_PickingProfile_Filter;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.X_MobileUI_UserProfile_Picking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class MobileUIPickingUserProfileRepositoryTest
{
	private MobileUIPickingUserProfileRepository repository;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());
		repository = new MobileUIPickingUserProfileRepository();
	}

	private I_MobileUI_UserProfile_Picking newProfileRecord()
	{
		final I_MobileUI_UserProfile_Picking record = newInstance(I_MobileUI_UserProfile_Picking.class);
		record.setIsActive(true);
		record.setName("test");
		record.setIsAllowAnyCustomer(true);
		record.setPickingJobAggregationType(X_MobileUI_UserProfile_Picking.PICKINGJOBAGGREGATIONTYPE_Sales_order);
		record.setCreateShipmentPolicy(X_MobileUI_UserProfile_Picking.CREATESHIPMENTPOLICY_DO_NOT_CREATE);
		return record;
	}

	private void newFilterRecord(
			@NonNull final I_MobileUI_UserProfile_Picking profileRecord,
			@NonNull final String filterType,
			final int seqNo)
	{
		final I_PickingProfile_Filter record = newInstance(I_PickingProfile_Filter.class);
		record.setIsActive(true);
		record.setMobileUI_UserProfile_Picking_ID(profileRecord.getMobileUI_UserProfile_Picking_ID());
		record.setFilterType(filterType);
		record.setSeqNo(seqNo);
		saveRecord(record);
	}

	@Nested
	class FilterGroupOrder
	{
		/**
		 * Two filter rows sharing a sequence number must not let the order in which they happen to be
		 * stored decide the order the operator sees. Both saving orders must produce the same result,
		 * i.e. the tie is broken by the group itself, in its declaration order.
		 */
		@Test
		void tiedSeqNo_customerStoredFirst_ordersByGroup()
		{
			final I_MobileUI_UserProfile_Picking profileRecord = newProfileRecord();
			saveRecord(profileRecord);
			newFilterRecord(profileRecord, X_PickingProfile_Filter.FILTERTYPE_Customer, 0);
			newFilterRecord(profileRecord, X_PickingProfile_Filter.FILTERTYPE_DeliveryDate, 0);

			final MobileUIPickingUserProfile profile = repository.getProfile();

			assertThat(profile.getFilterGroupsInOrder())
					.containsExactly(PickingJobFacetGroup.CUSTOMER, PickingJobFacetGroup.DELIVERY_DATE);
		}

		@Test
		void tiedSeqNo_deliveryDateStoredFirst_ordersByGroup()
		{
			final I_MobileUI_UserProfile_Picking profileRecord = newProfileRecord();
			saveRecord(profileRecord);
			newFilterRecord(profileRecord, X_PickingProfile_Filter.FILTERTYPE_DeliveryDate, 0);
			newFilterRecord(profileRecord, X_PickingProfile_Filter.FILTERTYPE_Customer, 0);

			final MobileUIPickingUserProfile profile = repository.getProfile();

			assertThat(profile.getFilterGroupsInOrder())
					.containsExactly(PickingJobFacetGroup.CUSTOMER, PickingJobFacetGroup.DELIVERY_DATE);
		}

		/**
		 * TC1's second half: the order must still hold once the profile has been saved back, which
		 * deletes and recreates the filter rows and resets their sequence numbers.
		 */
		@Test
		void tiedSeqNo_survivesAProfileEdit()
		{
			final I_MobileUI_UserProfile_Picking profileRecord = newProfileRecord();
			saveRecord(profileRecord);
			newFilterRecord(profileRecord, X_PickingProfile_Filter.FILTERTYPE_DeliveryDate, 0);
			newFilterRecord(profileRecord, X_PickingProfile_Filter.FILTERTYPE_Customer, 0);
			final ImmutableList<PickingJobFacetGroup> before = repository.getProfile().getFilterGroupsInOrder();

			repository.update(profile -> profile.toBuilder().isMassPrinting(true).build());

			assertThat(repository.getProfile().getFilterGroupsInOrder()).isEqualTo(before);
		}

		@Test
		void distinctSeqNo_ordersBySeqNo()
		{
			final I_MobileUI_UserProfile_Picking profileRecord = newProfileRecord();
			saveRecord(profileRecord);
			newFilterRecord(profileRecord, X_PickingProfile_Filter.FILTERTYPE_Customer, 20);
			newFilterRecord(profileRecord, X_PickingProfile_Filter.FILTERTYPE_DeliveryDate, 10);

			final MobileUIPickingUserProfile profile = repository.getProfile();

			assertThat(profile.getFilterGroupsInOrder())
					.containsExactly(PickingJobFacetGroup.DELIVERY_DATE, PickingJobFacetGroup.CUSTOMER);
		}
	}

	@Nested
	class IsMassPrinting
	{
		@Test
		void set_returnsTrue()
		{
			final I_MobileUI_UserProfile_Picking record = newProfileRecord();
			record.setIsMassPrinting(true);
			saveRecord(record);

			final MobileUIPickingUserProfile profile = repository.getProfile();

			assertThat(profile.isMassPrinting()).isTrue();
		}

		@Test
		void unset_returnsFalse()
		{
			final I_MobileUI_UserProfile_Picking record = newProfileRecord();
			record.setIsMassPrinting(false);
			saveRecord(record);

			final MobileUIPickingUserProfile profile = repository.getProfile();

			assertThat(profile.isMassPrinting()).isFalse();
		}

		@Test
		void noProfileRecord_returnsFalse()
		{
			// no profile record — should return default (false)
			final MobileUIPickingUserProfile profile = repository.getProfile();

			assertThat(profile.isMassPrinting()).isFalse();
		}
	}

	@Nested
	class IsShowQtyAvailableForLines
	{
		@Test
		void set_returnsTrue()
		{
			final I_MobileUI_UserProfile_Picking record = newProfileRecord();
			record.setIsShowQtyAvailableForLines(true);
			saveRecord(record);

			final MobileUIPickingUserProfile profile = repository.getProfile();

			assertThat(profile.isShowQtyAvailableForLines()).isTrue();
		}

		@Test
		void unset_returnsFalse()
		{
			final I_MobileUI_UserProfile_Picking record = newProfileRecord();
			record.setIsShowQtyAvailableForLines(false);
			saveRecord(record);

			final MobileUIPickingUserProfile profile = repository.getProfile();

			assertThat(profile.isShowQtyAvailableForLines()).isFalse();
		}

		@Test
		void noProfileRecord_returnsFalse()
		{
			// no profile record — should return default (false)
			final MobileUIPickingUserProfile profile = repository.getProfile();

			assertThat(profile.isShowQtyAvailableForLines()).isFalse();
		}
	}
}
