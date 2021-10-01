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

    if (workflowId) {
      const workflow = state.wfProcesses[workflowId];
      const { entries } = workflow.headerProperties;
      const newHeaderInfo = [entries];

      setHeaderInfo(newHeaderInfo);
    }
  }, [params, location]);

  return (
    <div className="level box p-4 header-caption">
      {headerInfo
        ? headerInfo.map((info, idx) => {
            return (
              <div key={idx} className={classnames('content', `header_info_${idx}`)}>
                {info.map(({ caption, value }, i) => {
                  return (
                    <p key={i} className="info_line">
                      {caption} : {value}
                    </p>
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
