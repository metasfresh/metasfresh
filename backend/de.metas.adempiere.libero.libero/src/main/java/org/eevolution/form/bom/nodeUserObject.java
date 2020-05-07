package org.eevolution.form.bom;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.compiere.model.X_M_Product;
import org.eevolution.model.X_PP_Product_BOM;
import org.eevolution.model.X_PP_Product_BOMLine;

public class nodeUserObject {
  public X_M_Product M_Product = null;
  public String displayString = ""; 
  public X_PP_Product_BOM bom = null;
  public X_PP_Product_BOMLine bomLine = null;
  public boolean isChosen = false;
  public boolean isCheckbox = false;
  public boolean isMandatory = false;
  public boolean isOptional = false;


  public nodeUserObject(String displayString, X_M_Product M_Product, X_PP_Product_BOM bom, X_PP_Product_BOMLine bomLine) {
    this.displayString = displayString;
    this.M_Product = M_Product;
    this.bom = bom;
    this.bomLine = bomLine;
    System.out.println("bomLine: " + bomLine);
    if(bom != null) {
    if(bomLine != null) {
       System.out.println("bomLine.getComponentType: " + bomLine.getComponentType());
       System.out.println("bomLine.COMPONENTTYPE_Component: " + X_PP_Product_BOMLine.COMPONENTTYPE_Component);
       if(bomLine.getComponentType().equals(X_PP_Product_BOMLine.COMPONENTTYPE_Component)) {
          System.out.println("setting checkbox to true");
          isCheckbox = true;
          isMandatory = true;
       }
       if(bomLine.getComponentType().equals(X_PP_Product_BOMLine.COMPONENTTYPE_Variant)) {
          System.out.println("setting checkbox to true");
          isCheckbox = true;
          isOptional = true;
       }
  
     

    }

    }
  }



  @Override
public String toString() {
    return displayString;
  }

}
