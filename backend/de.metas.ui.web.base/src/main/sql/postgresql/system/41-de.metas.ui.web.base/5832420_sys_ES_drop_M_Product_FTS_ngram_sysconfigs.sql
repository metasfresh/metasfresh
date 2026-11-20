/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

-- Typo-tolerance (pg_trgm) settings for the M_Product full-text search, read per search table via the ".product" suffix.
-- Same defaults as the C_BPartner search.

INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID /*From ID Server*/, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541863 /*From ID Server*/, 'S', TO_TIMESTAMP('2026-11-01 16:51:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Legt die Toleranz der (Trigramm-)Fuzzy-Suche für Produkte fest, damit auch bei Tippfehlern Treffer gefunden werden (pg_trgm). Der Wert ist die maximal erlaubte "Distanz" zwischen Suchbegriff und indiziertem Text: 0.0 = perfekter Treffer, 1.0 = kein Treffer. Da der Suchbegriff viel kürzer ist als der indizierte Text, muss der Wert hoch sein (0.7 = nur sehr nahe Treffer, 0.96 = tolerantere Suche).',
        'D', 'Y', 'de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.product',
        TO_TIMESTAMP('2026-11-01 16:51:00', 'YYYY-MM-DD HH24:MI:SS'), 100, '0.96')
;

INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID /*From ID Server*/, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541864 /*From ID Server*/, 'S', TO_TIMESTAMP('2026-11-01 16:52:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Maximale Anzahl tippfehlertoleranter Treffer der Produktsuche, die in der ersten Suchphase berücksichtigt werden. Das Limit wird vor allen anderen Filtern (z.B. Sicherheits- oder View-Filter) angewendet: Ein höherer Wert findet Treffer auch bei starken Tippfehlern, kann aber bei sehr großen Systemen die Performance beeinträchtigen. Gilt nur, wenn die Fuzzy-Suche aktiv ist (Distance < 1).',
        'D', 'Y', 'de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.product',
        TO_TIMESTAMP('2026-11-01 16:52:00', 'YYYY-MM-DD HH24:MI:SS'), 100, '1000')
;
