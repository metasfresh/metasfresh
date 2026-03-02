/*
 * #%L
 * de-metas-camel-scriptedadapter
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

/**
 * EPCIS 1.2 CD (Cross-Docking) Export Transform
 *
 * Converts metasfresh shipment JSON (from get_epcis_events_json_fn) into
 * an EPCIS 1.2 EPCISDocument XML containing 4 event types:
 *   1.0.1 ObjectEvent (LOT) — registers product lot (LGTIN + lot + best-before)
 *   1.1.1 AggregationEvent ADD (PACKING) — products (SGTINs) → crate (GRAI)
 *   3.3.3 AggregationEvent ADD (PICKING) — crates (GRAIs) → pallet (SSCC)
 *   3.5.1 AggregationEvent ADD (COMMISSIONING) — pallets (SSCCs) → shipment/DESADV
 *
 * Follows Migros M-EPCIS CD templates.
 */

/**
 * Build SGTIN URN from GTIN (14 or 13 digits).
 * Format: urn:epc:id:sgtin:<company_prefix>.<item_ref>.<serial>
 * For 13-digit GTIN: company prefix = first 7, item ref = next 6 (drop check digit)
 */
function buildSGTIN(gtin, serial) {
    if (!gtin) return '';
    var g = gtin.replace(/^0+/, '');
    // Pad to 13 digits
    while (g.length < 13) g = '0' + g;
    // Standard GS1 partition: 7-digit company prefix, 6-digit item reference (drop check digit at pos 13)
    var companyPrefix = g.substring(0, 7);
    var itemRef = g.substring(7, 13);
    return 'urn:epc:id:sgtin:' + companyPrefix + '.' + itemRef + '.' + (serial || '0');
}

/**
 * Build LGTIN URN (class-level with lot).
 * Format: urn:epc:class:lgtin:<company_prefix>.<item_ref>.10<lot>]C1
 * The ]C1 suffix is GS1 AI(10) FNC1 separator.
 */
function buildLGTIN(gtin, lotNumber) {
    if (!gtin) return '';
    var g = gtin.replace(/^0+/, '');
    while (g.length < 13) g = '0' + g;
    var companyPrefix = g.substring(0, 7);
    var itemRef = g.substring(7, 13);
    if (lotNumber) {
        return 'urn:epc:class:lgtin:' + companyPrefix + '.' + itemRef + '.10' + lotNumber + ']C1';
    }
    // No lot: use idpat with wildcard
    return 'urn:epc:idpat:sgtin:' + companyPrefix + '.' + itemRef + '.*';
}

/**
 * Build SGLN URN from GLN (13 digits).
 * Format: urn:epc:id:sgln:<company_prefix>.<location_ref>.<extension>
 * Extension 0 = base location.
 */
function buildSGLN(gln, extension) {
    if (!gln) return '';
    var g = gln.replace(/^0+/, '');
    while (g.length < 13) g = '0' + g;
    var companyPrefix = g.substring(0, 7);
    var locationRef = g.substring(7, 12);
    return 'urn:epc:id:sgln:' + companyPrefix + '.' + locationRef + '.' + (extension || '0');
}

/**
 * Build SSCC URN from SSCC18 string.
 * Format: urn:epc:id:sscc:<company_prefix>.<serial_reference>
 * For 18-digit SSCC: extension digit + company prefix (7) + serial ref (9) + check digit
 */
function buildSSCC(sscc18) {
    if (!sscc18) return '';
    // SSCC18: pos 0 = extension, pos 1-7 = company prefix, pos 8-16 = serial ref, pos 17 = check
    var companyPrefix = sscc18.substring(1, 8);
    var serialRef = sscc18.substring(0, 1) + sscc18.substring(8, 17);
    return 'urn:epc:id:sscc:' + companyPrefix + '.' + serialRef;
}

/**
 * Build GRAI URN from raw GRAI string.
 * If already a URN, return as-is.
 */
function buildGRAI(grai) {
    if (!grai) return '';
    if (grai.indexOf('urn:') === 0) return grai;
    // Raw GRAI: assume format company_prefix.asset_type.serial
    return 'urn:epc:id:grai:' + grai;
}

/**
 * Format best-before date for Migros ArticleAttrExpiryDate.
 * Input: YYYY-MM-DD, Output: YYYY-MM-DDT12:00:00.000+01:00
 */
function formatExpiryDate(dateStr) {
    if (!dateStr) return '';
    return dateStr + 'T12:00:00.000+01:00';
}

/**
 * Build bizTransaction PO reference.
 * Format: urn:epcglobal:cbv:bt:<buyer_gln>:<po_reference>
 */
function buildBizTransactionPO(buyerGLN, poReference) {
    if (!buyerGLN || !poReference) return '';
    return 'urn:epcglobal:cbv:bt:' + buyerGLN + ':' + poReference;
}

/**
 * Build bizTransaction DESADV reference.
 * Format: urn:epcglobal:cbv:bt:<supplier_gln>:<desadv_reference>
 */
function buildBizTransactionDESADV(supplierGLN, desadvReference) {
    if (!supplierGLN || !desadvReference) return '';
    return 'urn:epcglobal:cbv:bt:' + supplierGLN + ':' + desadvReference;
}

function escapeXml(str) {
    if (!str) return '';
    return ('' + str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

/**
 * Main transform function called by the Scripted Adapter.
 */
function transform(messageFromMetasfresh) {
    var data = JSON.parse(messageFromMetasfresh);

    var eventTime = data.movementDate || new Date().toISOString();
    var tz = data.timezone || '+01:00';
    var creationDate = new Date().toISOString();
    var supplierGLN = data.supplierGLN || '';
    var warehouseGLN = data.warehouseGLN || '';
    var buyerGLN = data.buyerGLN || '';
    var handoverGLN = data.handoverGLN || '';
    var dropshipGLN = data.dropshipGLN || '';
    var desadvRef = data.desadvReference || '';
    var poRef = data.poReference || '';
    var pallets = data.pallets || [];

    var warehouseSGLN = buildSGLN(warehouseGLN, '0');
    var supplierSGLN = buildSGLN(supplierGLN, '0');

    var events = '';

    // Collect all unique items for LOT events, all crates for PACKING, etc.
    var lotEvents = {}; // key: LGTIN, value: {sgtin, lgtin, expiryDate}
    var packingEvents = []; // {grai, sgtin, lgtin, quantity, uom, expiryDate, poRef, desadvRef}
    var pickingEvents = []; // {grai, palletSSCC, ...}
    var allPalletSSCCs = [];
    var allCrateGRAIs = {};

    for (var pi = 0; pi < pallets.length; pi++) {
        var pallet = pallets[pi];
        var palletSSCC = buildSSCC(pallet.sscc);
        allPalletSSCCs.push(palletSSCC);
        var crates = pallet.crates || [];

        for (var ci = 0; ci < crates.length; ci++) {
            var crate = crates[ci];
            var graiURN = buildGRAI(crate.grai);
            var items = crate.items || [];

            for (var ii = 0; ii < items.length; ii++) {
                var item = items[ii];
                var sgtin = buildSGTIN(item.productGTIN, '0');
                var lgtin = buildLGTIN(item.productGTIN, item.lotNumber);
                var lotKey = lgtin;

                // 1.0.1 LOT event — one per unique LGTIN
                if (!lotEvents[lotKey]) {
                    lotEvents[lotKey] = {
                        sgtin: sgtin,
                        lgtin: lgtin,
                        expiryDate: item.bestBeforeDate
                    };
                }

                // 1.1.1 PACKING event data
                packingEvents.push({
                    grai: graiURN,
                    sgtin: sgtin,
                    lgtin: lgtin,
                    quantity: item.quantity,
                    uom: item.uom || 'KGM',
                    expiryDate: item.bestBeforeDate
                });
            }

            // Track crate for picking
            if (graiURN) {
                allCrateGRAIs[graiURN] = palletSSCC;
                pickingEvents.push({
                    grai: graiURN,
                    palletSSCC: palletSSCC
                });
            }
        }
    }

    // === Event 1.0.1: ObjectEvent LOT ===
    var lotKeys = Object.keys(lotEvents);
    for (var li = 0; li < lotKeys.length; li++) {
        var lot = lotEvents[lotKeys[li]];
        events += '<ObjectEvent>';
        events += '<eventTime>' + escapeXml(eventTime) + '</eventTime>';
        events += '<recordTime>' + escapeXml(eventTime) + '</recordTime>';
        events += '<eventTimeZoneOffset>' + escapeXml(tz) + '</eventTimeZoneOffset>';
        events += '<epcList>';
        events += '<epc>' + escapeXml(lot.sgtin) + '</epc>';
        events += '</epcList>';
        events += '<action>ADD</action>';
        events += '<bizStep>urn:epcglobal:cbv:bizstep:other</bizStep>';
        events += '<readPoint><id>' + escapeXml(warehouseSGLN) + '</id></readPoint>';
        events += '<bizLocation><id>' + escapeXml(warehouseSGLN) + '</id></bizLocation>';
        events += '<extension>';
        events += '<quantityList>';
        events += '<quantityElement>';
        events += '<epcClass>' + escapeXml(lot.lgtin) + '</epcClass>';
        events += '</quantityElement>';
        events += '</quantityList>';
        if (lot.expiryDate) {
            events += '<ilmd>';
            events += '<migros:ArticleAttrExpiryDate xmlns:migros="http://migros.net/migros/">' + escapeXml(formatExpiryDate(lot.expiryDate)) + '</migros:ArticleAttrExpiryDate>';
            events += '</ilmd>';
        }
        events += '</extension>';
        events += '</ObjectEvent>';
    }

    // === Event 1.1.1: AggregationEvent PACKING (products → crate) ===
    // Group by GRAI
    var packingByGRAI = {};
    for (var pki = 0; pki < packingEvents.length; pki++) {
        var pe = packingEvents[pki];
        if (!pe.grai) continue;
        if (!packingByGRAI[pe.grai]) {
            packingByGRAI[pe.grai] = [];
        }
        packingByGRAI[pe.grai].push(pe);
    }

    var packGRAIs = Object.keys(packingByGRAI);
    for (var pgi = 0; pgi < packGRAIs.length; pgi++) {
        var grai = packGRAIs[pgi];
        var graiItems = packingByGRAI[grai];

        events += '<AggregationEvent>';
        events += '<eventTime>' + escapeXml(eventTime) + '</eventTime>';
        events += '<recordTime>' + escapeXml(eventTime) + '</recordTime>';
        events += '<eventTimeZoneOffset>' + escapeXml(tz) + '</eventTimeZoneOffset>';
        events += '<parentID>' + escapeXml(grai) + '</parentID>';
        events += '<childEPCs>';
        events += '<epc>' + escapeXml(graiItems[0].sgtin) + '</epc>';
        events += '</childEPCs>';
        events += '<action>ADD</action>';
        events += '<bizStep>urn:epcglobal:cbv:bizstep:packing</bizStep>';
        events += '<disposition>urn:epcglobal:cbv:disp:active</disposition>';
        events += '<readPoint><id>' + escapeXml(warehouseSGLN) + '</id></readPoint>';
        events += '<bizLocation><id>' + escapeXml(warehouseSGLN) + '</id></bizLocation>';
        events += '<bizTransactionList>';
        if (poRef && buyerGLN) {
            events += '<bizTransaction type="urn:epcglobal:cbv:btt:po">' + escapeXml(buildBizTransactionPO(buyerGLN, poRef)) + '</bizTransaction>';
        }
        if (desadvRef && supplierGLN) {
            events += '<bizTransaction type="urn:epcglobal:cbv:btt:desadv">' + escapeXml(buildBizTransactionDESADV(supplierGLN, desadvRef)) + '</bizTransaction>';
        }
        events += '</bizTransactionList>';
        events += '<extension>';
        events += '<childQuantityList>';
        for (var qi = 0; qi < graiItems.length; qi++) {
            var gi = graiItems[qi];
            events += '<quantityElement>';
            events += '<epcClass>' + escapeXml(gi.lgtin) + '</epcClass>';
            if (gi.quantity) {
                events += '<quantity>' + gi.quantity + '</quantity>';
                events += '<uom>' + escapeXml(gi.uom) + '</uom>';
            }
            if (gi.expiryDate) {
                events += '<migros:ArticleAttrExpiryDate xmlns:migros="http://migros.net/migros/">' + escapeXml(formatExpiryDate(gi.expiryDate)) + '</migros:ArticleAttrExpiryDate>';
            }
            events += '</quantityElement>';
        }
        events += '</childQuantityList>';
        events += '<sourceList>';
        events += '<source type="http://migros.net/migros/ele/dest/SU">' + escapeXml(supplierSGLN) + '</source>';
        events += '</sourceList>';
        events += '<destinationList>';
        if (handoverGLN) {
            events += '<destination type="http://migros.net/migros/ele/dest/DP">' + escapeXml(buildSGLN(handoverGLN, '0')) + '</destination>';
        }
        if (dropshipGLN) {
            events += '<destination type="http://migros.net/migros/ele/dest/UC">' + escapeXml(buildSGLN(dropshipGLN, '0')) + '</destination>';
        }
        events += '</destinationList>';
        events += '</extension>';
        events += '</AggregationEvent>';
    }

    // === Event 3.3.3: AggregationEvent PICKING (crates → pallet) ===
    // Group by pallet SSCC
    var pickingByPallet = {};
    for (var pki2 = 0; pki2 < pickingEvents.length; pki2++) {
        var pke = pickingEvents[pki2];
        if (!pke.palletSSCC) continue;
        if (!pickingByPallet[pke.palletSSCC]) {
            pickingByPallet[pke.palletSSCC] = [];
        }
        pickingByPallet[pke.palletSSCC].push(pke);
    }

    var pickPallets = Object.keys(pickingByPallet);
    for (var ppi = 0; ppi < pickPallets.length; ppi++) {
        var palletSSCC2 = pickPallets[ppi];
        var palletCrates = pickingByPallet[palletSSCC2];

        events += '<AggregationEvent>';
        events += '<eventTime>' + escapeXml(eventTime) + '</eventTime>';
        events += '<recordTime>' + escapeXml(eventTime) + '</recordTime>';
        events += '<eventTimeZoneOffset>' + escapeXml(tz) + '</eventTimeZoneOffset>';
        events += '<parentID>' + escapeXml(palletSSCC2) + '</parentID>';
        events += '<childEPCs>';
        for (var pci = 0; pci < palletCrates.length; pci++) {
            events += '<epc>' + escapeXml(palletCrates[pci].grai) + '</epc>';
        }
        events += '</childEPCs>';
        events += '<action>ADD</action>';
        events += '<bizStep>urn:epcglobal:cbv:bizstep:picking</bizStep>';
        events += '<disposition>urn:epcglobal:cbv:disp:in_progress</disposition>';
        events += '<readPoint><id>' + escapeXml(warehouseSGLN) + '</id></readPoint>';
        events += '<bizLocation><id>' + escapeXml(warehouseSGLN) + '</id></bizLocation>';
        events += '<bizTransactionList>';
        if (poRef && buyerGLN) {
            events += '<bizTransaction type="urn:epcglobal:cbv:btt:po">' + escapeXml(buildBizTransactionPO(buyerGLN, poRef)) + '</bizTransaction>';
        }
        if (desadvRef && supplierGLN) {
            events += '<bizTransaction type="urn:epcglobal:cbv:btt:desadv">' + escapeXml(buildBizTransactionDESADV(supplierGLN, desadvRef)) + '</bizTransaction>';
        }
        events += '</bizTransactionList>';
        events += '<extension>';
        // Picking events include childQuantityList per crate with item details
        events += '<childQuantityList>';
        for (var pci2 = 0; pci2 < palletCrates.length; pci2++) {
            var crateGrai = palletCrates[pci2].grai;
            // Find items for this crate from packingByGRAI
            var crateItems = packingByGRAI[crateGrai] || [];
            for (var cii = 0; cii < crateItems.length; cii++) {
                var ci2 = crateItems[cii];
                events += '<quantityElement>';
                events += '<epcClass>' + escapeXml(ci2.lgtin) + '</epcClass>';
                if (ci2.quantity) {
                    events += '<quantity>' + ci2.quantity + '</quantity>';
                    events += '<uom>' + escapeXml(ci2.uom) + '</uom>';
                }
                if (ci2.expiryDate) {
                    events += '<migros:ArticleAttrExpiryDate xmlns:migros="http://migros.net/migros/">' + escapeXml(formatExpiryDate(ci2.expiryDate)) + '</migros:ArticleAttrExpiryDate>';
                }
                events += '</quantityElement>';
            }
        }
        events += '</childQuantityList>';
        events += '<sourceList>';
        events += '<source type="http://migros.net/migros/ele/dest/SU">' + escapeXml(supplierSGLN) + '</source>';
        events += '</sourceList>';
        events += '<destinationList>';
        if (handoverGLN) {
            events += '<destination type="http://migros.net/migros/ele/dest/DP">' + escapeXml(buildSGLN(handoverGLN, '0')) + '</destination>';
        }
        if (dropshipGLN) {
            events += '<destination type="http://migros.net/migros/ele/dest/UC">' + escapeXml(buildSGLN(dropshipGLN, '0')) + '</destination>';
        }
        events += '</destinationList>';
        events += '</extension>';
        events += '</AggregationEvent>';
    }

    // === Event 3.5.1: AggregationEvent COMMISSIONING (pallets → shipment) ===
    if (allPalletSSCCs.length > 0) {
        events += '<AggregationEvent>';
        events += '<eventTime>' + escapeXml(eventTime) + '</eventTime>';
        events += '<recordTime>' + escapeXml(eventTime) + '</recordTime>';
        events += '<eventTimeZoneOffset>' + escapeXml(tz) + '</eventTimeZoneOffset>';
        // Commissioning: parentID is the first pallet's SSCC (represents the shipment unit)
        events += '<parentID>' + escapeXml(allPalletSSCCs[0]) + '</parentID>';
        events += '<childEPCs>';
        // All crate GRAIs across all pallets
        var allGRAIs = Object.keys(allCrateGRAIs);
        for (var agi = 0; agi < allGRAIs.length; agi++) {
            events += '<epc>' + escapeXml(allGRAIs[agi]) + '</epc>';
        }
        events += '</childEPCs>';
        events += '<action>ADD</action>';
        events += '<bizStep>urn:epcglobal:cbv:bizstep:commissioning</bizStep>';
        events += '<disposition>urn:epcglobal:cbv:disp:in_progress</disposition>';
        events += '<readPoint><id>' + escapeXml(warehouseSGLN) + '</id></readPoint>';
        events += '<bizLocation><id>' + escapeXml(warehouseSGLN) + '</id></bizLocation>';
        events += '<bizTransactionList>';
        if (poRef && buyerGLN) {
            events += '<bizTransaction type="urn:epcglobal:cbv:btt:po">' + escapeXml(buildBizTransactionPO(buyerGLN, poRef)) + '</bizTransaction>';
        }
        events += '</bizTransactionList>';
        events += '<extension>';
        events += '<sourceList>';
        events += '<source type="http://migros.net/migros/ele/dest/SU">' + escapeXml(supplierSGLN) + '</source>';
        events += '</sourceList>';
        events += '<destinationList>';
        if (handoverGLN) {
            events += '<destination type="http://migros.net/migros/ele/dest/DP">' + escapeXml(buildSGLN(handoverGLN, '0')) + '</destination>';
        }
        if (dropshipGLN) {
            events += '<destination type="http://migros.net/migros/ele/dest/UC">' + escapeXml(buildSGLN(dropshipGLN, '0')) + '</destination>';
        }
        events += '</destinationList>';
        events += '</extension>';
        events += '</AggregationEvent>';
    }

    // Wrap in EPCISDocument
    var xml = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>';
    xml += '<epcis:EPCISDocument xmlns:epcis="urn:epcglobal:epcis:xsd:1" xmlns:migros="http://migros.net/migros/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" creationDate="' + escapeXml(creationDate) + '" schemaVersion="1.2">';
    xml += '<EPCISBody>';
    xml += '<EventList>';
    xml += events;
    xml += '</EventList>';
    xml += '</EPCISBody>';
    xml += '</epcis:EPCISDocument>';

    return xml;
}
