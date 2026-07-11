import { trl } from '../../../utils/translations';
import React from 'react';
import PropTypes from 'prop-types';
import { appTrl } from '../utils';

export const WorkstationInfoComponent = ({ workstationInfo, currentWorkplaceName }) => {
  return (
    <table className="table view-header is-size-6">
      <tbody>
        <tr>
          <th>{appTrl('workstationName')}</th>
          <td>{workstationInfo.name}</td>
        </tr>
        {currentWorkplaceName && (
          // The operator's CURRENT active workplace (read from GET /workplace), not the workstation's
          // statically-linked workplace — so any drift between them is visible to the operator.
          <tr>
            <th>{trl('general.workplace')}</th>
            <td>{currentWorkplaceName}</td>
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
  currentWorkplaceName: PropTypes.string,
};

export default WorkstationInfoComponent;
