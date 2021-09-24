import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

class Launcher extends PureComponent {
  handleClick = (id) => {
    console.log(id);
  };

  render() {
    const { id, caption, wfProviderId, startedWFProcessId } = this.props;

    return (
      <div key={id} className="ml-3 mr-3 is-light launcher" onClick={() => this.handleClick(id)}>
        <div className="box">
          <div className="columns is-mobile">
            <div className="column is-12">
              <div className="columns">
                <div className="column is-size-4-mobile no-p">{caption}</div>
                <div className="column is-size-7 no-p">
                  {wfProviderId} - {startedWFProcessId}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Launcher.propTypes = {
  caption: PropTypes.string.isRequired,
  wfProviderId: PropTypes.string.isRequired,
  id: PropTypes.string.isRequired,
  startedWFProcessId: PropTypes.string,
};

export default Launcher;
