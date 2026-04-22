# Cloudflare Tunnel Binary Instructions

To complete the implementation, you need to:

1. Download the ARM64 cloudflared binary from: https://github.com/cloudflare/cloudflared/releases
   - Look for: cloudflared-linux-arm64
   - Version should be compatible with Android (statically linked)

2. Rename the binary to: `cloudflared` (no extension)

3. Place it in: `app/src/main/assets/cloudflared`

4. Make sure it's executable: `chmod +x cloudflared`

The binary will be automatically extracted to the app's files directory on first run and made executable.

Note: The TOKEN constant in TunnelManager.kt needs to be replaced with a real Cloudflare tunnel token for the domain app.shakti.buzz.