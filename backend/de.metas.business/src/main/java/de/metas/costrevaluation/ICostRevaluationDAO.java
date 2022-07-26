/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.costrevaluation;

import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_CostRevaluationLine;

import java.util.List;

public interface ICostRevaluationDAO  extends ISingletonService
{
	List<I_M_CostRevaluationLine> retrieveLinesByCostRevaluationId(@NonNull CostRevaluationId costRevaluationId);

}
