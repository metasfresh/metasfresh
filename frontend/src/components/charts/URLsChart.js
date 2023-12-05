import React from 'react';
import PropTypes from 'prop-types';

export const URLsChart = ({ data }) => {
  return (
    <div className="urls-chart">
      {data.map((item, index) => (
        <URLItem
          key={index}
          url={item.url}
          caption={item.caption}
          target={item.target}
        />
      ))}
    </div>
  );
};

URLsChart.propTypes = {
  data: PropTypes.array.isRequired,
};

const URLItem = ({ url, caption, target }) => {
  const captionEffective = caption ? caption : url;
  const targetEffective = target ? target : '_blank';

  return (
    <div className="item">
      <a href={url} target={targetEffective}>
        {captionEffective}
      </a>
    </div>
  );
};
URLItem.propTypes = {
  url: PropTypes.string.isRequired,
  caption: PropTypes.string,
  target: PropTypes.string,
};
