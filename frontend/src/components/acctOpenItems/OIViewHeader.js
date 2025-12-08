import React from 'react';
import PropTypes from 'prop-types';
import '../../assets/css/OIViewHeader.scss';

export const OIViewHeader_WINDOW_ID = 'SAPGLJournalSelectOpenItems';

const FIELDNAME_totalDebit = 'totalDebit';
const FIELDNAME_totalCredit = 'totalCredit';
const FIELDNAME_balance = 'balance';

export const OIViewHeader = ({ headerProperties }) => {
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
    <table className="oiViewHeader">
      <thead>
        <tr>
          <th>{entriesByFieldName[FIELDNAME_totalDebit]?.caption}</th>
          <th>{entriesByFieldName[FIELDNAME_totalCredit]?.caption}</th>
          <th>{entriesByFieldName[FIELDNAME_balance]?.caption}</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{entriesByFieldName[FIELDNAME_totalDebit]?.value}</td>
          <td>{entriesByFieldName[FIELDNAME_totalCredit]?.value}</td>
          <td>{entriesByFieldName[FIELDNAME_balance]?.value}</td>
        </tr>
      </tbody>
    </table>
  );
};

OIViewHeader.propTypes = {
  headerProperties: PropTypes.object,
};
