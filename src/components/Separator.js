import React from 'react';

const Separator = props => {
  const { title } = props;

  return (
    <div className="separator col-12">
      <span className="separator-title">{title}</span>
    </div>
  );
};

export default Separator;
