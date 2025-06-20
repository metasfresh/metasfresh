/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.request.alberta;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
public class JsonAlbertaPatient
{
	@Schema
	@Nullable
	private String hospitalIdentifier;

	@Schema(hidden = true)
	private boolean hospitalIdentifierSet;

	@Schema
	@Nullable
	private LocalDate dischargeDate;

	@Schema(hidden = true)
	private boolean dischargeDateSet;

	@Schema
	@Nullable
	private String payerIdentifier;

	@Schema(hidden = true)
	private boolean payerIdentifierSet;

	@Schema
	@Nullable
	private String payerType;

	@Schema(hidden = true)
	private boolean payerTypeSet;

	@Schema
	@Nullable
	private String numberOfInsured;

	@Schema(hidden = true)
	private boolean numberOfInsuredSet;

	@Schema
	@Nullable
	private LocalDate copaymentFrom;

	@Schema(hidden = true)
	private boolean copaymentFromSet;

	@Schema
	@Nullable
	private LocalDate copaymentTo;

	@Schema(hidden = true)
	private boolean copaymentToSet;

	@Schema
	@Nullable
	private Boolean isTransferPatient;

	@Schema(hidden = true)
	private boolean transferPatientSet;

	@Schema
	@Nullable
	private Boolean isIVTherapy;

	@Schema(hidden = true)
	private boolean ivTherapySet;

	@Schema
	@Nullable
	private String fieldNurseIdentifier;

	@Schema(hidden = true)
	private boolean fieldNurseIdentifierSet;

	@Schema
	@Nullable
	private String deactivationReason;

	@Schema(hidden = true)
	private boolean deactivationReasonSet;

	@Schema
	@Nullable
	private LocalDate deactivationDate;

	@Schema(hidden = true)
	private boolean deactivationDateSet;

	@Schema
	@Nullable
	private String deactivationComment;

	@Schema(hidden = true)
	private boolean deactivationCommentSet;

	@Schema
	@Nullable
	private String classification;

	@Schema(hidden = true)
	private boolean classificationSet;

	@Schema
	@Nullable
	private BigDecimal careDegree;

	@Schema(hidden = true)
	private boolean careDegreeSet;

	@Schema
	@Nullable
	private Instant createdAt;

	@Schema(hidden = true)
	private boolean createdAtSet;

	@Schema
	@Nullable
	private String createdByIdentifier;

	@Schema(hidden = true)
	private boolean createdByIdentifierSet;

	@Schema
	@Nullable
	private Instant updatedAt;

	@Schema(hidden = true)
	private boolean updatedAtSet;

	@Schema
	@Nullable
	private String updateByIdentifier;

	@Schema(hidden = true)
	private boolean updateByIdentifierSet;

	public void setHospitalIdentifier(@Nullable final String hospitalIdentifier)
	{
		this.hospitalIdentifier = hospitalIdentifier;
		this.hospitalIdentifierSet = true;
	}

	public void setDischargeDate(@Nullable final LocalDate dischargeDate)
	{
		this.dischargeDate = dischargeDate;
		this.dischargeDateSet = true;
	}

	public void setPayerIdentifier(@Nullable final String payerIdentifier)
	{
		this.payerIdentifier = payerIdentifier;
		this.payerIdentifierSet = true;
	}

	public void setPayerType(@Nullable final String payerType)
	{
		this.payerType = payerType;
		this.payerTypeSet = true;
	}

	public void setNumberOfInsured(@Nullable final String numberOfInsured)
	{
		this.numberOfInsured = numberOfInsured;
		this.numberOfInsuredSet = true;
	}

	public void setCopaymentFrom(@Nullable final LocalDate copaymentFrom)
	{
		this.copaymentFrom = copaymentFrom;
		this.copaymentFromSet = true;
	}

	public void setCopaymentTo(@Nullable final LocalDate copaymentTo)
	{
		this.copaymentTo = copaymentTo;
		this.copaymentToSet = true;
	}

	public void setIsTransferPatient(@Nullable final Boolean isTransferPatient)
	{
		this.isTransferPatient = isTransferPatient;
		this.transferPatientSet = true;
	}

	public void setIVTherapy(@Nullable final Boolean IVTherapy)
	{
		this.isIVTherapy = IVTherapy;
		this.ivTherapySet = true;
	}

	public void setFieldNurseIdentifier(@Nullable final String fieldNurseIdentifier)
	{
		this.fieldNurseIdentifier = fieldNurseIdentifier;
		this.fieldNurseIdentifierSet = true;
	}

	public void setDeactivationReason(@Nullable final String deactivationReason)
	{
		this.deactivationReason = deactivationReason;
		this.deactivationReasonSet = true;
	}

	public void setDeactivationDate(@Nullable final LocalDate deactivationDate)
	{
		this.deactivationDate = deactivationDate;
		this.deactivationDateSet = true;
	}

	public void setDeactivationComment(@Nullable final String deactivationComment)
	{
		this.deactivationComment = deactivationComment;
		this.deactivationCommentSet = true;
	}

	public void setClassification(@Nullable final String classification)
	{
		this.classification = classification;
		this.classificationSet = true;
	}

	public void setCareDegree(@Nullable final BigDecimal careDegree)
	{
		this.careDegree = careDegree;
		this.careDegreeSet = true;
	}

	public void setCreatedAt(@Nullable final Instant createdAt)
	{
		this.createdAt = createdAt;
		this.createdAtSet = true;
	}

	public void setCreatedByIdentifier(@Nullable final String createdByIdentifier)
	{
		this.createdByIdentifier = createdByIdentifier;
		this.createdByIdentifierSet = true;
	}

	public void setUpdatedAt(@Nullable final Instant updatedAt)
	{
		this.updatedAt = updatedAt;
		this.updatedAtSet = true;
	}

	public void setUpdateByIdentifier(@Nullable final String updateByIdentifier)
	{
		this.updateByIdentifier = updateByIdentifier;
		this.updateByIdentifierSet = true;
	}

}
