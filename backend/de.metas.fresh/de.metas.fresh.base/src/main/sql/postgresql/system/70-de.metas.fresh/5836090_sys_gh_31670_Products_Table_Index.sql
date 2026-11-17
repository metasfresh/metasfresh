/*
 * #%L
 * de.metas.fresh.base
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

CREATE INDEX IF NOT EXISTS m_product_ingredients_m_product_id_seqno_index
    ON public.m_product_ingredients (m_product_id, seqno)
;

CREATE INDEX IF NOT EXISTS m_product_allergen_m_product_id_m_allergen_id_index
    ON public.m_product_allergen (m_product_id, m_allergen_id)
;

CREATE INDEX IF NOT EXISTS m_product_certificate_m_product_id_m_label_id_index
    ON public.m_product_certificate (m_product_id, m_label_id)
;

CREATE INDEX IF NOT EXISTS m_product_nutrition_m_product_id_m_nutrition_fact_id_index
    ON public.m_product_nutrition (m_product_id, m_nutrition_fact_id)
;
