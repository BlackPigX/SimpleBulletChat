package BulletChat;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Copyright(c)lbhbinhao@163.com
 *
 * @author liubinhao
 * @date 2021/1/14
 * ++++ ______                           ______             ______
 * +++/     /|                         /     /|           /     /|
 * +/_____/  |                       /_____/  |         /_____/  |
 * |     |   |                      |     |   |        |     |   |
 * |     |   |                      |     |   |________|     |   |
 * |     |   |                      |     |  /         |     |   |
 * |     |   |                      |     |/___________|     |   |
 * |     |   |___________________   |     |____________|     |   |
 * |     |  /                  / |  |     |   |        |     |   |
 * |     |/ _________________/  /   |     |  /         |     |  /
 * |_________________________|/b    |_____|/           |_____|/
 */

public class BulletChatHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 用于记录和管理所有客户端的channel
    public static ChannelGroup channels =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
//        // 获取客户端传输过来的消息
//        String content = msg.text();
//        System.err.println("收到消息："+ content);
//        channels.writeAndFlush(new TextWebSocketFrame(content));
//        System.err.println("写出消息完成："+content);
//    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        String channelId = ctx.channel().id().asShortText();
        System.out.println("客户端被移除，channelId为：" + channelId);
        channels.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        channels.remove(ctx.channel());
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端传输过来的消息
        String content = msg.text();
        System.err.println("收到消息："+ content);
        channels.writeAndFlush(new TextWebSocketFrame(content));
        System.err.println("写出消息完成："+content);
    }
}
