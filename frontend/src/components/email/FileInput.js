/*
 * Code extracted and upgrade from no longer maintained react-file-input project
 * https://github.com/captivationsoftware/react-file-input
 */

import React, { useState } from 'react';
import PropTypes from 'prop-types';

const STYLES = {
  parent: {
    position: 'relative',
  },
  file: {
    position: 'absolute',
    top: 0,
    left: 0,
    opacity: 0,
    width: '100%',
    zIndex: 1,
  },
  text: {
    position: 'relative',
    zIndex: -1,
  },
};

const extractFilenameFromPath = (filepath) => {
  return filepath ? filepath.split(/([\\/])/g).pop() : '';
};

const FileInput = ({
  onChange,
  name,
  className,
  disabled,
  placeholder,
  accept,
}) => {
  const [filename, setFilename] = useState('');

  const handleChange = (event) => {
    setFilename(extractFilenameFromPath(event.target.value));

    if (onChange) {
      const file = event.target.files[0];
      onChange(file);
    }
  };

  return (
    <div style={STYLES.parent}>
      <input
        type="file"
        name={name}
        className={className}
        onChange={handleChange}
        disabled={disabled}
        accept={accept}
        style={STYLES.file}
      />
      <input
        type="text"
        tabIndex="-1"
        name={`${name}_filename`}
        value={filename}
        className={className}
        onChange={() => {}}
        placeholder={placeholder}
        disabled={disabled}
        style={STYLES.text}
      />
    </div>
  );
};

FileInput.propTypes = {
  name: PropTypes.string,
  placeholder: PropTypes.string,
  className: PropTypes.string,
  onChange: PropTypes.func,
  disabled: PropTypes.bool,
  accept: PropTypes.string,
};

export default FileInput;
