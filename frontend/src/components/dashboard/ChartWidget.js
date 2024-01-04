import React, { Component } from 'react';
import PropTypes from 'prop-types';
import RawChart from '../charts/RawChart';
import counterpart from 'counterpart';

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

  toggleMenu = (opt) => {
    const { toggleWidgetMenu } = this.state;
    this.setState({
      toggleWidgetMenu: typeof opt === 'boolean' ? opt : !toggleWidgetMenu,
    });
  };

  render() {
    const {
      text,
      framework,
      data,
      noData,
      maximizeWidget,
      index,
      idMaximized,
      id,
      chartType,
      caption,
      fields,
      groupBy,
      editmode,
      openChartOptions,
    } = this.props;
    const { toggleWidgetMenu, height } = this.state;
    const isMaximized = idMaximized === id;

    if (!isMaximized && typeof idMaximized === 'number') return false;

    const isRenderChartContent = !framework || !!data;

    return (
      <div>
        <div
          className={
            'draggable-widget-header ' +
            (editmode ? 'draggable-widget-edited ' : '')
          }
          onDoubleClick={
            editmode || !maximizeWidget
              ? undefined
              : () => {
                  isMaximized ? maximizeWidget() : maximizeWidget(id);
                  this.toggleMenu(false);
                }
          }
        >
          <p className="draggable-widget-title">
            {text}
            {editmode && openChartOptions && (
              <span
                className="chart-edit-mode"
                onClick={() => openChartOptions(id)}
              >
                <i className="meta-icon-settings" />
              </span>
            )}
          </p>
          {!editmode && !framework && (
            <i
              className="draggable-widget-icon meta-icon-down-1 input-icon-sm"
              onClick={() => this.toggleMenu()}
            />
          )}
          {toggleWidgetMenu && !editmode && (
            <div className="draggable-widget-menu">
              {isMaximized ? (
                <span
                  onClick={() => {
                    maximizeWidget();
                    this.toggleMenu(false);
                  }}
                >
                  {counterpart.translate('dashboard.item.minimize')}
                </span>
              ) : (
                <span
                  onClick={() => {
                    maximizeWidget(id);
                    this.toggleMenu(false);
                  }}
                >
                  {counterpart.translate('dashboard.item.maximize')}
                </span>
              )}
            </div>
          )}
        </div>

        <div className="draggable-widget-body">
          {isRenderChartContent ? (
            <RawChart
              {...{
                index,
                chartType,
                caption,
                fields,
                groupBy,
                height,
                isMaximized,
                id,
                data,
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

ChartWidget.propTypes = {
  text: PropTypes.string,
  framework: PropTypes.bool,
  data: PropTypes.object,
  noData: PropTypes.any,
  maximizeWidget: PropTypes.func,
  index: PropTypes.number,
  isMaximized: PropTypes.bool,
  chartType: PropTypes.string,
  caption: PropTypes.string,
  fields: PropTypes.any,
  groupBy: PropTypes.object,
  editmode: PropTypes.bool,
  openChartOptions: PropTypes.func,
  id: PropTypes.number,
  idMaximized: PropTypes.number,
};

export default ChartWidget;
