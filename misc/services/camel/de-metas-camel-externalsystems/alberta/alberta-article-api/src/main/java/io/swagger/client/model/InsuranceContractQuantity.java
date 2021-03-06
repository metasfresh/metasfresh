/*
 * #%L
 * alberta-article-api
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

/*
 * Artikel - Warenwirtschaft (Basis)
 * Synchronisation der Artikel mit Kumavision
 *
 * OpenAPI spec version: 1.0.2
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;
/**
 * Maximalmenge
 */
@Schema(description = "Maximalmenge")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-03-11T10:09:42.333Z[GMT]")
public class InsuranceContractQuantity {
  @SerializedName("pcn")
  private String pcn = null;

  @SerializedName("quantity")
  private BigDecimal quantity = null;

  @SerializedName("unit")
  private String unit = null;

  @SerializedName("archived")
  private Boolean archived = null;

  public InsuranceContractQuantity pcn(String pcn) {
    this.pcn = pcn;
    return this;
  }

   /**
   * PZN für Maximalmenge
   * @return pcn
  **/
  @Schema(example = "54658452", description = "PZN für Maximalmenge")
  public String getPcn() {
    return pcn;
  }

  public void setPcn(String pcn) {
    this.pcn = pcn;
  }

  public InsuranceContractQuantity quantity(BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }

   /**
   * Anzahl
   * @return quantity
  **/
  @Schema(example = "3", description = "Anzahl")
  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public InsuranceContractQuantity unit(String unit) {
    this.unit = unit;
    return this;
  }

   /**
   * Mengeneinheit (mögliche Werte &#x27;Stk&#x27; oder &#x27;Ktn&#x27;)
   * @return unit
  **/
  @Schema(example = "Stk", description = "Mengeneinheit (mögliche Werte 'Stk' oder 'Ktn')")
  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public InsuranceContractQuantity archived(Boolean archived) {
    this.archived = archived;
    return this;
  }

   /**
   * Maximalmenge nicht mehr gültig - archiviert
   * @return archived
  **/
  @Schema(example = "false", description = "Maximalmenge nicht mehr gültig - archiviert")
  public Boolean isArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InsuranceContractQuantity insuranceContractQuantity = (InsuranceContractQuantity) o;
    return Objects.equals(this.pcn, insuranceContractQuantity.pcn) &&
        Objects.equals(this.quantity, insuranceContractQuantity.quantity) &&
        Objects.equals(this.unit, insuranceContractQuantity.unit) &&
        Objects.equals(this.archived, insuranceContractQuantity.archived);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pcn, quantity, unit, archived);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InsuranceContractQuantity {\n");
    
    sb.append("    pcn: ").append(toIndentedString(pcn)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
    sb.append("    archived: ").append(toIndentedString(archived)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
