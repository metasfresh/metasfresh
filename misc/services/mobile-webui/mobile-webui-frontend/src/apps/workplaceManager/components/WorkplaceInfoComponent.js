import { trl } from '../../../utils/translations';
import React from 'react';
import PropTypes from 'prop-types';

export const WorkplaceInfoComponent = ({ workplaceInfo }) => {
  return (
    <table className="table view-header is-size-6">
      <tbody>
        <tr>
          <th>{trl('workplaceManager.workplaceName')}</th>
          <td>{workplaceInfo.name}</td>
        </tr>
        <tr>
          <th>{trl('workplaceManager.warehouseName')}</th>
          <td>{workplaceInfo.warehouseName}</td>
        </tr>
        <tr>
          <th>{trl('workplaceManager.isUserAssigned')}</th>
          <td>{trl(workplaceInfo.userAssigned ? 'general.Yes' : 'general.No')}</td>
        </tr>
      </tbody>
    </table>
  );
};

WorkplaceInfoComponent.propTypes = {
  workplaceInfo: PropTypes.object.isRequired,
};

export default WorkplaceInfoComponent;
