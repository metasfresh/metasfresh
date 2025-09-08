import React from 'react';
import PropTypes from 'prop-types';
import '../../assets/css/AcctSimulationViewHeader.scss';

export const AcctSimulationViewHeader_WINDOW_ID = 'acctSimulation';

const FIELDNAME_totalDebit_DC = 'totalDebit_DC';
const FIELDNAME_totalCredit_DC = 'totalCredit_DC';
const FIELDNAME_balance_DC = 'balance_DC';

const FIELDNAME_totalDebit_LC = 'totalDebit_LC';
const FIELDNAME_totalCredit_LC = 'totalCredit_LC';
const FIELDNAME_balance_LC = 'balance_LC';

export const AcctSimulationViewHeader = ({ headerProperties }) => {
  const propsGroups = headerProperties?.groups;
  if (!propsGroups || propsGroups.length <= 0) {
    return null;
  }

  const entriesByFieldName = propsGroups
    .flatMap((propsGroup) => propsGroup.entries ?? [])
    .reduce((acc, entry) => {
      acc[entry.fieldName] = entry;
      return acc;
    }, {});

  return (
    <table className="acctSimulationViewHeader">
      <tbody>
        <tr>
          <td className="label">
            {entriesByFieldName[FIELDNAME_totalDebit_DC]?.caption}
          </td>
          <td className="value">
            {entriesByFieldName[FIELDNAME_totalDebit_DC]?.value}
          </td>
          <td className="label">
            {entriesByFieldName[FIELDNAME_totalDebit_LC]?.caption}
          </td>
          <td className="value">
            {entriesByFieldName[FIELDNAME_totalDebit_LC]?.value}
          </td>
        </tr>
        <tr>
          <td className="label">
            {entriesByFieldName[FIELDNAME_totalCredit_DC]?.caption}
          </td>
          <td className="value">
            {entriesByFieldName[FIELDNAME_totalCredit_DC]?.value}
          </td>
          <td className="label">
            {entriesByFieldName[FIELDNAME_totalCredit_LC]?.caption}
          </td>
          <td className="value">
            {entriesByFieldName[FIELDNAME_totalCredit_LC]?.value}
          </td>
        </tr>
        <tr>
          <td className="label">
            {entriesByFieldName[FIELDNAME_balance_DC]?.caption}
          </td>
          <td className="value">
            {entriesByFieldName[FIELDNAME_balance_DC]?.value}
          </td>
          <td className="label">
            {entriesByFieldName[FIELDNAME_balance_LC]?.caption}
          </td>
          <td className="value">
            {entriesByFieldName[FIELDNAME_balance_LC]?.value}
          </td>
        </tr>
      </tbody>
    </table>
  );
};

AcctSimulationViewHeader.propTypes = {
  headerProperties: PropTypes.object,
};
