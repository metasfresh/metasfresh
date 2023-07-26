/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaBPartner;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaCareGiver;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaContact;
import de.metas.common.bpartner.v2.request.alberta.JsonAlbertaPatient;
import de.metas.common.bpartner.v2.request.alberta.JsonBPartnerRole;
import de.metas.common.bpartner.v2.request.alberta.JsonCompositeAlbertaBPartner;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.vertical.healthcare.alberta.bpartner.albertabpartner.AlbertaBPartner;
import de.metas.vertical.healthcare.alberta.bpartner.albertabpartner.AlbertaBPartnerRepository;
import de.metas.vertical.healthcare.alberta.bpartner.caregiver.AlbertaCaregiver;
import de.metas.vertical.healthcare.alberta.bpartner.caregiver.AlbertaCaregiverRepository;
import de.metas.vertical.healthcare.alberta.bpartner.caregiver.CaregiverType;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatient;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatientRepository;
import de.metas.vertical.healthcare.alberta.bpartner.patient.DeactivationReasonType;
import de.metas.vertical.healthcare.alberta.bpartner.patient.PayerType;
import de.metas.vertical.healthcare.alberta.bpartner.role.AlbertaRole;
import de.metas.vertical.healthcare.alberta.bpartner.role.AlbertaRoleRepository;
import de.metas.vertical.healthcare.alberta.bpartner.role.AlbertaRoleType;
import de.metas.vertical.healthcare.alberta.bpartner.user.AlbertaUser;
import de.metas.vertical.healthcare.alberta.bpartner.user.AlbertaUserRepository;
import de.metas.vertical.healthcare.alberta.bpartner.user.GenderType;
import de.metas.vertical.healthcare.alberta.bpartner.user.TitleType;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbertaBPartnerCompositeService
{
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;

	private final AlbertaBPartnerRepository albertaBPartnerRepository;
	private final AlbertaPatientRepository albertaPatientRepository;
	private final AlbertaCaregiverRepository albertaCaregiverRepository;
	private final AlbertaRoleRepository albertaRoleRepository;
	private final AlbertaUserRepository albertaUserRepository;

	public AlbertaBPartnerCompositeService(
			@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService,
			@NonNull final AlbertaBPartnerRepository albertaBPartnerRepository,
			@NonNull final AlbertaPatientRepository albertaPatientRepository,
			@NonNull final AlbertaCaregiverRepository albertaCaregiverRepository,
			@NonNull final AlbertaRoleRepository albertaRoleRepository,
			@NonNull final AlbertaUserRepository albertaUserRepository)
	{
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
		this.albertaBPartnerRepository = albertaBPartnerRepository;
		this.albertaPatientRepository = albertaPatientRepository;
		this.albertaCaregiverRepository = albertaCaregiverRepository;
		this.albertaRoleRepository = albertaRoleRepository;
		this.albertaUserRepository = albertaUserRepository;
	}

	public void upsertAlbertaCompositeInfo(
			@NonNull final OrgId orgId,
			@NonNull final BPartnerId bPartnerId,
			@NonNull final JsonCompositeAlbertaBPartner request,
			@NonNull final SyncAdvise syncAdvise)
	{
		if (request.getJsonAlbertaBPartner() != null)
		{
			syncAlbertaBPartner(syncAdvise, request.getJsonAlbertaBPartner(), bPartnerId);
		}

		if (request.getJsonAlbertaPatient() != null)
		{
			syncAlbertaPatient(orgId, syncAdvise, request.getJsonAlbertaPatient(), bPartnerId);
		}

		if (request.getJsonAlbertaCareGivers() != null)
		{
			syncCareGivers(orgId, syncAdvise, request.getJsonAlbertaCareGivers(), bPartnerId);
		}

		if (request.getRole() != null)
		{
			syncAlbertaRole(syncAdvise, request.getRole(), bPartnerId);
		}
	}

	public void upsertAlbertaContact(
			@NonNull final UserId userId,
			@NonNull final JsonAlbertaContact request,
			@NonNull final SyncAdvise syncAdvise)
	{
		final Optional<AlbertaUser> existingAlbertaUserOpt = albertaUserRepository.getByUserId(userId);

		if (existingAlbertaUserOpt.isPresent() && !syncAdvise.getIfExists().isUpdate())
		{
			return;
		}
		else if (!existingAlbertaUserOpt.isPresent() && syncAdvise.getIfNotExists().isFail())
		{
			throw MissingResourceException.builder()
					.resourceName("AlbertaUser")
					.resourceIdentifier(String.valueOf(userId))
					.build();
		}

		final AlbertaUser existingAlbertaUser = existingAlbertaUserOpt
				.orElse(AlbertaUser.builder()
								.userId(userId)
								.build());

		final AlbertaUser.AlbertaUserBuilder syncedAlbertaUserBuilder = existingAlbertaUser.toBuilder();

		if (request.isTitleSet())
		{
			syncedAlbertaUserBuilder.title(TitleType.ofCodeNullable(request.getTitle()));
		}

		if (request.isGenderSet())
		{
			syncedAlbertaUserBuilder.gender(GenderType.ofCodeNullable(request.getGender()));
		}

		if (request.isTimestampSet())
		{
			syncedAlbertaUserBuilder.timestamp(request.getTimestamp());
		}

		albertaUserRepository.save(syncedAlbertaUserBuilder.build());
	}

	private void syncAlbertaBPartner(
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final JsonAlbertaBPartner request,
			@NonNull final BPartnerId bPartnerId)
	{
		final Optional<AlbertaBPartner> existingAlbertaBPartnerOpt = albertaBPartnerRepository.getByPartnerId(bPartnerId);

		if (existingAlbertaBPartnerOpt.isPresent() && !syncAdvise.getIfExists().isUpdate())
		{
			return;
		}
		else if (!existingAlbertaBPartnerOpt.isPresent() && syncAdvise.getIfNotExists().isFail())
		{
			throw MissingResourceException.builder()
					.resourceName("AlbertaBPartner")
					.resourceIdentifier(String.valueOf(bPartnerId))
					.build();
		}

		final AlbertaBPartner existingAlbertaBPartner = existingAlbertaBPartnerOpt
				.orElse(AlbertaBPartner.builder()
								.bPartnerId(bPartnerId)
								.build());

		final AlbertaBPartner.AlbertaBPartnerBuilder syncedAlbertaBuilder = existingAlbertaBPartner.toBuilder();

		if (request.isTitleSet())
		{
			syncedAlbertaBuilder.title(request.getTitle());
		}

		if (request.isTitleShortSet())
		{
			syncedAlbertaBuilder.titleShort(request.getTitleShort());
		}

		if (request.isArchivedSet())
		{
			syncedAlbertaBuilder.isArchived(Boolean.TRUE.equals(request.getIsArchived()));
		}

		if (request.isTimestampSet())
		{
			syncedAlbertaBuilder.timestamp(request.getTimestamp());
		}

		albertaBPartnerRepository.save(syncedAlbertaBuilder.build());
	}

	private void syncAlbertaPatient(
			@NonNull final OrgId orgId,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final JsonAlbertaPatient request,
			@NonNull final BPartnerId bPartnerId)
	{
		final Optional<AlbertaPatient> existingAlbertaPatientOpt = albertaPatientRepository.getByBPartnerId(bPartnerId);

		if (existingAlbertaPatientOpt.isPresent() && !syncAdvise.getIfExists().isUpdate())
		{
			return;
		}
		else if (!existingAlbertaPatientOpt.isPresent() && syncAdvise.getIfNotExists().isFail())
		{
			throw MissingResourceException.builder()
					.resourceName("AlbertaPatient")
					.resourceIdentifier(String.valueOf(bPartnerId))
					.build();
		}

		final AlbertaPatient existingAlbertaPatient = existingAlbertaPatientOpt
				.orElse(AlbertaPatient.builder()
								.bPartnerId(bPartnerId)
								.build());

		final AlbertaPatient.AlbertaPatientBuilder syncedAlbertaBuilder = existingAlbertaPatient.toBuilder();

		if (request.isHospitalIdentifierSet())
		{
			if (request.getHospitalIdentifier() == null)
			{
				syncedAlbertaBuilder.hospitalId(null);
			}
			else
			{
				final ExternalIdentifier hospitalExternalIdentifier = ExternalIdentifier.of(request.getHospitalIdentifier());
				final JsonMetasfreshId jsonMetasfreshId = externalReferenceRestControllerService.getJsonMetasfreshIdFromExternalReference(orgId, hospitalExternalIdentifier, BPartnerExternalReferenceType.BPARTNER)
						.orElseThrow(() -> MissingResourceException.builder()
								.resourceIdentifier(hospitalExternalIdentifier.getRawValue())
								.resourceName("BPartner")
								.parentResource(request)
								.build());

				syncedAlbertaBuilder.hospitalId(BPartnerId.ofRepoId(jsonMetasfreshId.getValue()));
			}
		}

		if (request.isDischargeDateSet())
		{
			syncedAlbertaBuilder.dischargeDate(request.getDischargeDate());
		}

		if (request.isPayerIdentifierSet())
		{
			if (request.getPayerIdentifier() == null)
			{
				syncedAlbertaBuilder.payerId(null);
			}
			else
			{
				final ExternalIdentifier payerExternalIdentifier = ExternalIdentifier.of(request.getPayerIdentifier());
				final JsonMetasfreshId jsonMetasfreshId = externalReferenceRestControllerService.getJsonMetasfreshIdFromExternalReference(orgId, payerExternalIdentifier, BPartnerExternalReferenceType.BPARTNER)
						.orElseThrow(() -> MissingResourceException.builder()
								.resourceIdentifier(payerExternalIdentifier.getRawValue())
								.resourceName("BPartner")
								.parentResource(request)
								.build());

				syncedAlbertaBuilder.payerId(BPartnerId.ofRepoId(jsonMetasfreshId.getValue()));
			}
		}

		if (request.isPayerTypeSet())
		{
			final PayerType payerType = PayerType.ofCodeNullable(request.getPayerType());
			syncedAlbertaBuilder.payerType(payerType);
		}

		if (request.isNumberOfInsuredSet())
		{
			syncedAlbertaBuilder.numberOfInsured(request.getNumberOfInsured());
		}

		if (request.isCopaymentFromSet())
		{
			syncedAlbertaBuilder.copaymentFrom(request.getCopaymentFrom());
		}

		if (request.isCopaymentToSet())
		{
			syncedAlbertaBuilder.copaymentTo(request.getCopaymentTo());
		}

		if (request.isTransferPatientSet())
		{
			syncedAlbertaBuilder.isTransferPatient(Boolean.TRUE.equals(request.getIsTransferPatient()));
		}

		if (request.isIvTherapySet())
		{
			syncedAlbertaBuilder.isIVTherapy(Boolean.TRUE.equals(request.getIsIVTherapy()));
		}

		if (request.isDeactivationReasonSet())
		{
			final DeactivationReasonType deactivationReasonType =
					DeactivationReasonType.ofCodeNullable(request.getDeactivationReason());

			syncedAlbertaBuilder.deactivationReason(deactivationReasonType);
		}

		if (request.getDeactivationDate() != null)
		{
			syncedAlbertaBuilder.deactivationDate(request.getDeactivationDate());
		}

		if (request.isDeactivationCommentSet())
		{
			syncedAlbertaBuilder.deactivationComment(request.getDeactivationComment());
		}

		if (request.isCreatedAtSet())
		{
			syncedAlbertaBuilder.createdAt(request.getCreatedAt());
		}

		if (request.isUpdatedAtSet())
		{
			syncedAlbertaBuilder.updatedAt(request.getUpdatedAt());
		}

		if (request.isCreatedByIdentifierSet())
		{
			if (request.getCreatedByIdentifier() == null)
			{
				syncedAlbertaBuilder.createdById(null);
			}
			else
			{
				final ExternalIdentifier createdByExternalIdentifier = ExternalIdentifier.of(request.getCreatedByIdentifier());
				final JsonMetasfreshId jsonMetasfreshId = externalReferenceRestControllerService.getJsonMetasfreshIdFromExternalReference(orgId, createdByExternalIdentifier, ExternalUserReferenceType.USER_ID)
						.orElseThrow(() -> MissingResourceException.builder()
								.resourceIdentifier(createdByExternalIdentifier.getRawValue())
								.resourceName("User")
								.parentResource(request)
								.build());

				syncedAlbertaBuilder.createdById(UserId.ofRepoId(jsonMetasfreshId.getValue()));
			}
		}

		if (request.isUpdateByIdentifierSet())
		{
			if (request.getUpdateByIdentifier() == null)
			{
				syncedAlbertaBuilder.updatedById(null);
			}
			else
			{
				final ExternalIdentifier updatedByExternalIdentifier = ExternalIdentifier.of(request.getUpdateByIdentifier());
				final JsonMetasfreshId jsonMetasfreshId = externalReferenceRestControllerService.getJsonMetasfreshIdFromExternalReference(orgId, updatedByExternalIdentifier, ExternalUserReferenceType.USER_ID)
						.orElseThrow(() -> MissingResourceException.builder()
								.resourceIdentifier(updatedByExternalIdentifier.getRawValue())
								.resourceName("User")
								.parentResource(request)
								.build());

				syncedAlbertaBuilder.updatedById(UserId.ofRepoId(jsonMetasfreshId.getValue()));
			}
		}

		if (request.isFieldNurseIdentifierSet())
		{
			if (request.getFieldNurseIdentifier() == null)
			{
				syncedAlbertaBuilder.fieldNurseId(null);
			}
			else
			{
				final ExternalIdentifier fieldNurseExternalIdentifier = ExternalIdentifier.of(request.getFieldNurseIdentifier());
				final JsonMetasfreshId jsonMetasfreshId = externalReferenceRestControllerService.getJsonMetasfreshIdFromExternalReference(orgId, fieldNurseExternalIdentifier, ExternalUserReferenceType.USER_ID)
						.orElseThrow(() -> MissingResourceException.builder()
								.resourceIdentifier(fieldNurseExternalIdentifier.getRawValue())
								.resourceName("User")
								.parentResource(request)
								.build());

				syncedAlbertaBuilder.fieldNurseId(UserId.ofRepoId(jsonMetasfreshId.getValue()));
			}
		}

		albertaPatientRepository.save(syncedAlbertaBuilder.build());
	}

	private void syncCareGivers(
			@NonNull final OrgId orgId,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final List<JsonAlbertaCareGiver> careGiverList,
			@NonNull final BPartnerId patientBPartnerId)
	{
		careGiverList.forEach(careGiver -> syncCareGiver(orgId, patientBPartnerId, syncAdvise, careGiver));
	}

	private void syncCareGiver(
			@NonNull final OrgId orgId,
			@NonNull final BPartnerId patientBPartnerId,
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final JsonAlbertaCareGiver request)
	{
		final ExternalIdentifier careGiverExternalIdentifier = ExternalIdentifier.of(request.getCaregiverIdentifier());
		final JsonMetasfreshId jsonMetasfreshId = externalReferenceRestControllerService.getJsonMetasfreshIdFromExternalReference(orgId, careGiverExternalIdentifier, BPartnerExternalReferenceType.BPARTNER)
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceIdentifier(careGiverExternalIdentifier.getRawValue())
						.resourceName("BPartner")
						.parentResource(request)
						.build());

		final BPartnerId careGiverBPartnerID = BPartnerId.ofRepoId(jsonMetasfreshId.getValue());

		final Optional<AlbertaCaregiver> existingAlbertaCaregiverOpt = albertaCaregiverRepository.getByPatientAndCaregiver(patientBPartnerId, careGiverBPartnerID);

		if (existingAlbertaCaregiverOpt.isPresent() && !syncAdvise.getIfExists().isUpdate())
		{
			return;
		}
		else if (!existingAlbertaCaregiverOpt.isPresent() && syncAdvise.getIfNotExists().isFail())
		{
			throw MissingResourceException.builder()
					.resourceName("AlbertaCareGiver")
					.resourceIdentifier("PatientBPartnerId:" + patientBPartnerId.getRepoId() + " CareGiverBPartnerID:" + careGiverBPartnerID.getRepoId())
					.build();
		}

		final AlbertaCaregiver existingAlbertaCareGiver = existingAlbertaCaregiverOpt
				.orElse(AlbertaCaregiver.builder()
								.bPartnerId(patientBPartnerId)
								.caregiverId(careGiverBPartnerID)
								.build());

		final AlbertaCaregiver.AlbertaCaregiverBuilder syncedAlbertaCareGiver = existingAlbertaCareGiver.toBuilder();

		if (request.isTypeSet())
		{
			syncedAlbertaCareGiver.type(CaregiverType.ofCodeNullable(request.getType()));
		}

		if (request.isLegalCarerSet())
		{
			syncedAlbertaCareGiver.isLegalCarer(Boolean.TRUE.equals(request.getIsLegalCarer()));
		}

		albertaCaregiverRepository.save(syncedAlbertaCareGiver.build());
	}

	private void syncAlbertaRole(
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final JsonBPartnerRole request,
			@NonNull final BPartnerId bPartnerId)
	{
		final List<AlbertaRole> albertaRoles = albertaRoleRepository.getByPartnerId(bPartnerId);

		final Optional<AlbertaRole> exitingRecordForRequestedRoleOpt = albertaRoles.stream()
				.filter(albertaRole -> albertaRole.getRole().name().equals(request.name()))
				.findFirst();

		if (exitingRecordForRequestedRoleOpt.isPresent())
		{
			return;
		}
		else if (syncAdvise.getIfNotExists().isFail())
		{
			throw MissingResourceException.builder()
					.resourceName("AlbertaRole")
					.resourceIdentifier("BPartnerId:" + bPartnerId.getRepoId() + " Role:" + request.name())
					.build();
		}

		final AlbertaRole albertaRole = AlbertaRole.builder()
				.bPartnerId(bPartnerId)
				.role(AlbertaRoleType.valueOf(request.name()))
				.build();

		albertaRoleRepository.save(albertaRole);
	}
}
