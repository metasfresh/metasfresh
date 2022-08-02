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

import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(position = 10)
	@Nullable
	private String hospitalIdentifier;

	@ApiModelProperty(hidden = true)
	private boolean hospitalIdentifierSet;

	@ApiModelProperty(position = 20)
	@Nullable
	private LocalDate dischargeDate;

	@ApiModelProperty(hidden = true)
	private boolean dischargeDateSet;

	@ApiModelProperty(position = 30)
	@Nullable
	private String payerIdentifier;

	@ApiModelProperty(hidden = true)
	private boolean payerIdentifierSet;

	@ApiModelProperty(position = 40)
	@Nullable
	private String payerType;

	@ApiModelProperty(hidden = true)
	private boolean payerTypeSet;

	@ApiModelProperty(position = 50)
	@Nullable
	private String numberOfInsured;

	@ApiModelProperty(hidden = true)
	private boolean numberOfInsuredSet;

	@ApiModelProperty(position = 60)
	@Nullable
	private LocalDate copaymentFrom;

	@ApiModelProperty(hidden = true)
	private boolean copaymentFromSet;

	@ApiModelProperty(position = 70)
	@Nullable
	private LocalDate copaymentTo;

	@ApiModelProperty(hidden = true)
	private boolean copaymentToSet;

	@ApiModelProperty(position = 80)
	@Nullable
	private Boolean isTransferPatient;

	@ApiModelProperty(hidden = true)
	private boolean transferPatientSet;

	@ApiModelProperty(position = 90)
	@Nullable
	private Boolean isIVTherapy;

	@ApiModelProperty(hidden = true)
	private boolean ivTherapySet;

	@ApiModelProperty(position = 100)
	@Nullable
	private String fieldNurseIdentifier;

	@ApiModelProperty(hidden = true)
	private boolean fieldNurseIdentifierSet;

	@ApiModelProperty(position = 110)
	@Nullable
	private String deactivationReason;

	@ApiModelProperty(hidden = true)
	private boolean deactivationReasonSet;

	@ApiModelProperty(position = 120)
	@Nullable
	private LocalDate deactivationDate;

	@ApiModelProperty(hidden = true)
	private boolean deactivationDateSet;

	@ApiModelProperty(position = 130)
	@Nullable
	private String deactivationComment;

	@ApiModelProperty(hidden = true)
	private boolean deactivationCommentSet;

	@ApiModelProperty(position = 140)
	@Nullable
	private String classification;

	@ApiModelProperty(hidden = true)
	private boolean classificationSet;

	@ApiModelProperty(position = 150)
	@Nullable
	private BigDecimal careDegree;

	@ApiModelProperty(hidden = true)
	private boolean careDegreeSet;

	@ApiModelProperty(position = 160)
	@Nullable
	private Instant createdAt;

	@ApiModelProperty(hidden = true)
	private boolean createdAtSet;

	@ApiModelProperty(position = 170)
	@Nullable
	private String createdByIdentifier;

	@ApiModelProperty(hidden = true)
	private boolean createdByIdentifierSet;

	@ApiModelProperty(position = 180)
	@Nullable
	private Instant updatedAt;

	@ApiModelProperty(hidden = true)
	private boolean updatedAtSet;

	@ApiModelProperty(position = 190)
	@Nullable
	private String updateByIdentifier;

	@ApiModelProperty(hidden = true)
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
