#!/usr/bin/env bash
# regenerate-jaxb-sources.sh
#
# Regenerates JAXB Java sources from XSD/WSDL schemas for all affected modules.
#
# Run this script after changing any *.xsd or *.wsdl schema file, then commit
# the resulting changes to the java-xjc* directories together with the schema change.
#
# Usage:
#   ./misc/dev-support/scripts/regenerate-jaxb-sources.sh
#
# The script must be run from the metasfresh repository root.

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "${SCRIPT_DIR}/../../.." && pwd)"

cd "${REPO_ROOT}"

# All modules that have JAXB sources checked into java-xjc* directories.
# Update this list if a new module is added with maven-jaxb2-plugin generation.
MODULES=(
    "backend/de.metas.banking/de.metas.banking.camt53"
    "backend/de.metas.payment.esr"
    "backend/de.metas.payment.sepa/schema-pain_001_01_03_ch_02"
    "backend/de.metas.payment.sepa/schema-pain_008_003_02"
    "backend/de.metas.printing.common/de.metas.printing.esb.base"
    "backend/de.metas.shipper.gateway.dpd"
    "backend/de.metas.shipper.gateway.go"
    "backend/de.metas.vertical.pharma.msv3.schema.v1"
    "backend/de.metas.vertical.pharma.msv3.schema.v2"
    "backend/vertical-healthcare_ch/forum_datenaustausch_ch.invoice_440_request"
    "backend/vertical-healthcare_ch/forum_datenaustausch_ch.invoice_440_response"
    "backend/vertical-healthcare_ch/forum_datenaustausch_ch.invoice_450_request"
    "backend/vertical-healthcare_ch/forum_datenaustausch_ch.invoice_450_response"
    "misc/services/camel/de-metas-camel-edi"
)

MVN="${MVN:-mvn}"
PROFILE="regenerate-jaxb-sources"
FAILED=()

echo "Regenerating JAXB sources for ${#MODULES[@]} modules..."
echo "Profile: -P${PROFILE}"
echo ""

for MODULE in "${MODULES[@]}"; do
    echo "==> ${MODULE}"
    # shellcheck disable=SC2086  # MVN is intentionally unquoted to allow "mvn -s settings.xml" style overrides
    if (cd "${REPO_ROOT}/${MODULE}" && ${MVN} generate-sources -P"${PROFILE}" -q --no-transfer-progress); then
        echo "    OK"
    else
        echo "    FAILED"
        FAILED+=("${MODULE}")
    fi
done

echo ""
if [[ ${#FAILED[@]} -eq 0 ]]; then
    echo "All modules regenerated successfully."
    echo ""
    echo "Next steps:"
    echo "  1. Review the changes: git diff --name-only | grep java-xjc"
    echo "  2. Commit the changed java-xjc files together with the XSD change in a single commit."
else
    echo "The following modules failed to regenerate:"
    for M in "${FAILED[@]}"; do
        echo "  - ${M}"
    done
    exit 1
fi
