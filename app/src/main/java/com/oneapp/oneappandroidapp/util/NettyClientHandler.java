package com.oneapp.oneappandroidapp.util;

import com.alibaba.fastjson.JSONObject;
import com.oneapp.oneappandroidapp.netty_model.MessagePush;
import com.oneapp.oneappandroidapp.netty_model.MyString;
import com.oneapp.oneappandroidapp.netty_model.User;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) { //连接成功后的动作
        System.out.println("与server连接成功");
        User user= new User((long)11,null,null);
        //连接上之后默认发送本机的user信息
        JSONObject userPush = (JSONObject) JSONObject.toJSON(user);
        String userPushStr = JSONObject.toJSONString(userPush);
        ctx.channel().writeAndFlush(userPushStr);
        System.out.println("向server发送了User信息: "+user);

        MyString myString = new MyString("这是测试内容");
        JSONObject strJson = (JSONObject) JSONObject.toJSON(myString);
        String strPush = JSONObject.toJSONString(strJson);
        ctx.channel().writeAndFlush(strPush);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String msgrReceived = (String) msg;
        JSONObject msgReceived = JSONObject.parseObject(msgrReceived);
        if(msgReceived.containsKey("str")) {
            //判断为MyString
            MyString myString = JSONObject.toJavaObject(msgReceived, MyString.class);
            System.out.println(ctx.channel().remoteAddress()
                    + ":收到服务端消息：" + myString.getStr());

        }
        else if(msgReceived.containsKey("messagePushTitle")){//判断为发过来的推送消息
            MessagePush messagePushReceived = JSONObject
                    .toJavaObject(msgReceived, MessagePush.class);
            System.out.println(ctx.channel().remoteAddress()
                    + ":收到服务端推送：标题："+ messagePushReceived.getMessagePushTitle()
                    + "内容：" + messagePushReceived.getMessagePushContent());
        }
    }

    /**
     * spring boot 中为channelRead0方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {

    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if(IdleState.WRITER_IDLE.equals(event.state())) {
                MyString myString = new MyString("heartbeat");
                JSONObject strJson = (JSONObject) JSONObject.toJSON(myString);
                String strPush = JSONObject.toJSONString(strJson);
                ctx.channel().writeAndFlush(strPush);
                System.out.println("向Server发送心跳：heartbeat");
            }
        }
        super.userEventTriggered(ctx, evt);

    }

}
