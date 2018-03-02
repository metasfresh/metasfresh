import React, { Component } from 'react';
import RawChart from '../charts/RawChart';

export class ChartWidget extends Component {
  constructor(props) {
    super(props);
    this.state = {
      toggleWidgetMenu: false,
      height: 400,
    };
  }

  componentDidMount() {
    const { text } = this.props;

    this.setState({
      captionHandler: text,
    });
  }

  handleClickOutside = () => {
    this.setState({
      toggleWidgetMenu: false,
    });
  };

  toggleMenu = opt => {
    const { toggleWidgetMenu } = this.state;
    this.setState({
      toggleWidgetMenu: typeof opt === 'boolean' ? opt : !toggleWidgetMenu,
    });
  };

  render() {
    const {
      text,
      framework,
      noData,
      maximizeWidget,
      index,
      idMaximized,
      id,
      chartType,
      caption,
      fields,
      groupBy,
      pollInterval,
      editmode,
      handleChartOptions,
    } = this.props;

    const { toggleWidgetMenu, height } = this.state;

    const isMaximized = idMaximized === id;
    if (!isMaximized && typeof idMaximized === 'number') return false;

    return (
      <div>
        <div
          className={
            'draggable-widget-header ' +
            (editmode ? 'draggable-widget-edited ' : '')
          }
          onDoubleClick={
            !editmode &&
            (() => {
              isMaximized ? maximizeWidget() : maximizeWidget(id);
              this.toggleMenu(false);
            })
          }
        >
          <p className="draggable-widget-title">
            {text}
            {editmode ? (
              <span
                className="chart-edit-mode"
                onClick={() => handleChartOptions(true, text, id, false)}
              >
                <i className="meta-icon-settings" />
              </span>
            ) : (
              ''
            )}
          </p>
          {!editmode &&
            !framework && (
              <i
                className="draggable-widget-icon meta-icon-down-1 input-icon-sm"
                onClick={() => this.toggleMenu()}
              />
            )}
          {toggleWidgetMenu &&
            !editmode && (
              <div className="draggable-widget-menu">
                {isMaximized ? (
                  <span
                    onClick={() => {
                      maximizeWidget();
                      this.toggleMenu(false);
                    }}
                  >
                    Minimize
                  </span>
                ) : (
                  <span
                    onClick={() => {
                      maximizeWidget(id);
                      this.toggleMenu(false);
                    }}
                  >
                    Maximize
                  </span>
                )}
              </div>
            )}
        </div>

        <div className="draggable-widget-body">
          {!framework ? (
            <RawChart
              {...{
                index,
                chartType,
                caption,
                fields,
                groupBy,
                pollInterval,
                height,
                isMaximized,
                id,
                noData,
              }}
              responsive={true}
              chartTitle={text}
            />
          ) : (
            <div>{chartType}</div>
          )}
        </div>
      </div>
    );
  }
}

export default ChartWidget;
