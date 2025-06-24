# Etapa 1: Construcción
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Etapa 2: Producción lo sirve con NGINX
FROM nginx:alpine

# Copiamos la configuración personalizada de NGINX
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copiamos el build generado en la etapa anterior
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

