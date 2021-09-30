import React, { useEffect, useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import { useStore } from 'react-redux';
import classnames from 'classnames';

const ViewHeader = () => {
  const location = useLocation();
  const params = useParams();
  const state = useStore().getState();
  const [headerInfo, setHeaderInfo] = useState(null);

  useEffect(() => {
    const { workflowId } = params;
    const workflow = state.wfProcesses[workflowId];
    const { entries } = workflow.headerProperties;
    const newHeaderInfo = [entries];

    setHeaderInfo(newHeaderInfo);
  }, [params, location]);

  return (
    <div className="title is-4 header-caption">
      WFProcess Caption HEADER
      {headerInfo
        ? headerInfo.map((info, idx) => {
            return (
              <div key={idx} className={classnames(`header_info_${idx}`)}>
                {info.map(({ caption, value }, i) => {
                  return (
                    <div key={i} className="info_line">
                      {caption} : {value}
                    </div>
                  );
                })}
              </div>
            );
          })
        : null}
    </div>
  );
};

export default ViewHeader;
