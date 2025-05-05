import { trl } from '../../../utils/translations';
import React from 'react';
import PropTypes from 'prop-types';
import { appTrl } from '../utils';

export const WorkstationInfoComponent = ({ workstationInfo }) => {
  return (
    <table className="table view-header is-size-6">
      <tbody>
        <tr>
          <th>{appTrl('workstationName')}</th>
          <td>{workstationInfo.name}</td>
        </tr>
        {workstationInfo.workplaceName && (
          <tr>
            <th>{trl('general.workplace')}</th>
            <td>{workstationInfo.workplaceName}</td>
          </tr>
        )}
        <tr>
          <th>{appTrl('isUserAssigned')}</th>
          <td>{trl(workstationInfo.userAssigned ? 'general.Yes' : 'general.No')}</td>
        </tr>
      </tbody>
    </table>
  );
};

WorkstationInfoComponent.propTypes = {
  workstationInfo: PropTypes.object.isRequired,
};

export default WorkstationInfoComponent;
