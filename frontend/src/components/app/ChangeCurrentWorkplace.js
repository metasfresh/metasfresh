import React from 'react';
import { useWorkplaces } from '../../api/userSession';

export const STATIC_MODAL_TYPE_ChangeCurrentWorkplace =
  'changeCurrentWorkplace';

const ChangeCurrentWorkplace = () => {
  const {
    isWorkplacesLoading,
    isWorkplacesEnabled,
    currentWorkplace,
    changeCurrentWorkplace,
    availableWorkplaces,
  } = useWorkplaces({ includeAvailable: true });

  if (isWorkplacesLoading) {
    // TODO use spinner
    return <div>Loading...</div>;
  }
  if (!isWorkplacesEnabled) {
    return <div>Not enabled</div>;
  }

  const onChange = (workplace) => {
    changeCurrentWorkplace(workplace);
  };

  return (
    <div className="panel-modal-content js-panel-modal-content container-fluid">
      <div className="window-wrapper">
        <div className="sections-wrapper">
          <div className="panel panel-spaced panel-distance panel-bordered panel-primary">
            {availableWorkplaces &&
              availableWorkplaces.map((workplace) => (
                <div key={workplace.key}>
                  <div>&nbsp;</div>

                  <div className="row">
                    <div className="col-lg-6 col-md-6">
                      <label className="input-checkbox">
                        <input
                          type="radio"
                          value={workplace.key}
                          checked={workplace.key === currentWorkplace?.key}
                          onChange={() => onChange(workplace)}
                        />
                        &nbsp;&nbsp; {workplace.caption}
                        <span className="input-checkbox-tick" />
                      </label>
                    </div>
                  </div>
                </div>
              ))}
            <div>&nbsp;</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChangeCurrentWorkplace;
