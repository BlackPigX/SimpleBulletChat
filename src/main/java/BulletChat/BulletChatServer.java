package BulletChat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public enum  BulletChatServer {
    /**
     * Server instance
     */
    SERVER;

    private BulletChatServer(){
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup  = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(mainGroup,subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new BulletChatInitializer());
        ChannelFuture future = server.bind(9123);
    }

    public static void main(String[] args) {

    }

}
