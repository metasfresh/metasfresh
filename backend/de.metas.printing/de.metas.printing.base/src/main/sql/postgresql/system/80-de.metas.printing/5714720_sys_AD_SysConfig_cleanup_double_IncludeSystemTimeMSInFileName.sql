DELETE
FROM ad_sysconfig
WHERE ad_sysconfig_id = 541681
; /* duplicated sysconfig de.metas.printing.IncludeSystemTimeMSInFileName from 2023-12-22 */

UPDATE ad_sysconfig
SET ad_client_id=0, updated=TO_TIMESTAMP('2024-01-03 09:26:08.164', 'YYYY-MM-DD HH24:MI:SS.US'), updatedby=100,
    description='If set to Y and something is printed with an AD_PrinterHW that has OutputType=Store, then metasfresh prepends "<Timestamp>_" to the PDF filename.
<Timestamp> is the server''s current timestamp in milliseconds since 01-01-1970.
The goal of this is to avoid name collisions in case a printing queue item is printed more than once.
If *both* this system-config *and* de.metas.printing.IncludePInstanceIdInFileName are set to Y, then the filename contains first the AD_PInstance_ID, followed by the timestamp.'
WHERE ad_sysconfig_id = 541326
; /* sysconfig de.metas.printing.IncludeSystemTimeMSInFileName from 2020-06-23 */


UPDATE ad_sysconfig
SET ad_client_id=0, isactive='Y', updated=TO_TIMESTAMP('2024-01-03 09:26:08.164', 'YYYY-MM-DD HH24:MI:SS.US'), updatedby=100
WHERE ad_sysconfig_id = 541325
; /* de.metas.printing.StorePDFBaseDirectory */
