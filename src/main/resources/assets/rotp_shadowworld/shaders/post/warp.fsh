#version 150
uniform sampler2D Sampler0;
uniform vec2 ScreenSize;
uniform float Time;

in vec2 texCoord;

void main() {
    vec2 center = vec2(0.5, 0.5);
    float dist = distance(texCoord, center);
    vec2 warpCoord = texCoord;

    warpCoord.x += sin(dist * 20.0 + Time * 2.0) * 0.15 * (1.0 - dist);
    warpCoord.y += cos(dist * 20.0 + Time * 2.0) * 0.15 * (1.0 - dist);

    gl_FragColor = texture(Sampler0, warpCoord);
}