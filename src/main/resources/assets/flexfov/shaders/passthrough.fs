#version 130

in vec2 texcoord;

uniform sampler2D texFront;

out vec4 color;

void main(void) {
    color = texture(texFront, texcoord/2 + 0.5);
}