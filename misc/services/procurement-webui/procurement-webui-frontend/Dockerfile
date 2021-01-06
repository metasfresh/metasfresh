FROM node:10-alpine as build-step

LABEL maintainer="info@metasfresh.com"
LABEL runinfo="Run this image with -e PUBLIC_URL=https://yourdomain.org"

COPY . /

RUN yarn install
RUN yarn build

RUN chmod a+x ./nginx-config/docker-entrypoint.d/*

# Step 2
FROM nginx

# We copy our files to staging folders. They will be copied to the actual folders at startup, to prevent overmounting.
# Also see the script in nginx-config.
RUN mkdir -p /usr/share/nginx/prepared_nginx_files/html
COPY --from=build-step build /usr/share/nginx/prepared_nginx_files/html

# Make sure that the default files are gone, because the startup-script won't overwrite existing files
RUN rm -r /usr/share/nginx/html/*
#COPY --from=build-step build /usr/share/nginx/html

COPY --from=build-step ./nginx-config/docker-entrypoint.d /docker-entrypoint.d
 