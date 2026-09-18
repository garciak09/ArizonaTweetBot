FROM ubuntu:latest
LABEL authors="kevingarcia"

ENTRYPOINT ["top", "-b"]