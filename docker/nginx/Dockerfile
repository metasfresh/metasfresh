FROM nginx
COPY dist /usr/share/nginx/html

COPY config.js /usr/share/nginx/html

#add entry-script
COPY start_webui.sh /
RUN chmod +x /start_webui.sh

ENTRYPOINT ["/start_webui.sh"]
