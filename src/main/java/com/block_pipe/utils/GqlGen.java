/*
 * @Author: your name
 * @Date: 2022-04-25 09:14:11
 * @LastEditTime: 2022-06-06 10:23:50
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /datapipe/src/main/java/com/datapipe/Util.java
 */

package com.block_pipe.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.block_pipe.model.Cls;
import com.block_pipe.model.Cls.StreamField;
import com.google.protobuf.Descriptors.FieldDescriptor;

public class GqlGen {
       public static String getGen(byte[] streamBytes) throws Exception {
              StreamField streamField = Cls.StreamField.parseFrom(streamBytes);
              // int insertType = 0;
              // String dataKey = "rank";
              // String typeName = "flow";
              String spaceName = "trans_real";
              byte[] data = streamField.getSpecialField().toByteArray();

              // return
              // insertType==0?insertVertexGen(spaceName,dataKey,typeName,data):insertEdgeGen(spaceName,dataKey,typeName,data);
              return insertEdgeGen(spaceName, data);
       }

       // 获取插入点的语句
       public static String insertVertexGen(String spaceName, String dataKey, String tagName, byte[] data)
                     throws Exception {
              Cls.Node node = Cls.Node.parseFrom(data);
              String keyValue = null;
              List<Object> nameList = new ArrayList<>();
              List<Object> valueList = new ArrayList<>();
              for (final FieldDescriptor field : node.getDescriptorForType().getFields()) {
                     if (!node.hasField(field)) {
                            continue;
                     } else if (field.getName().equals(dataKey)) {
                            keyValue = "`" + node.getField(field).toString() + "`";
                            continue;
                     }
                     valueList.add(node.getField(field).toString());
                     nameList.add(field.getName());

              }
              String field_string = "(" + nameList.stream().map(Objects::toString).collect(Collectors.joining(","))
                            + ")";
              String value_string = "(" + valueList.stream().map(Objects::toString).collect(Collectors.joining(","))
                            + ")";
              String insert_vertex = "USE " + spaceName + ";INSERT VERTEX`" + tagName + "`" + field_string + "VALUES '"
                            + keyValue + "': " + value_string + ";";
              return insert_vertex;
       }

       // 获取插入边的语句
       // INSERT EDGE
       // `flow`(`timestamp`,`coin`,`value`,`transHash`,`gasUsed`,`gaslimit`,`fee`,`fromType`,`toType`,`transType`,`isLoop`,`status`,`rank`)
       // VALUES
       // "0x115f2962A839cA4deD94C594D07bfd7BbC59e7b3"->"0xc597A5d92F5dC1137D847db733cfe02c6d209f13"@1440018583:
       // (1440018583, "ETH", 2.79234778E18,
       // "0xbda1499f0b2e4865226bb93514a1d823138d3525d5d2cdefb2e2b469c48ef352",
       // 21000.0, 90000.0, 1.05E15, "normal", "normal", "ETH", 0, 1,
       // 14400185833181463967)
       public static String insertEdgeGen(String spaceName, byte[] data) throws Exception {
              Cls.Edge edge = Cls.Edge.parseFrom(data);
              String keyValue = null;
              String from = null;
              String to = null;
              List<Object> nameList = new ArrayList<>();
              List<Object> valueList = new ArrayList<>();
              for (final FieldDescriptor field : edge.getDescriptorForType().getFields()) {
                     if (!edge.hasField(field)) {
                            continue;
                     } else {
                            String name = "`" + field.getName() + "`";
                            String value = edge.getField(field).toString();
                            if (field.getType().toString().equals("STRING")) {
                                   value = "'" + value + "'";
                            }
                            if (field.getName().equals("rank")) {
                                   keyValue = edge.getField(field).toString();
                                   continue;
                            } else if (field.getName().equals("_from")) {
                                   from = "'" + edge.getField(field).toString() + "'";
                                   continue;
                            } else if (field.getName().equals("to")) {
                                   to = "'" + edge.getField(field).toString() + "'";
                                   continue;
                            }
                            valueList.add(value);
                            nameList.add(name);
                     }

              }
              String field_string = "(" + nameList.stream().map(Objects::toString).collect(Collectors.joining(","))
                            + ")";
              String value_string = "(" + valueList.stream().map(Objects::toString).collect(Collectors.joining(","))
                            + ")";
              String insert_edge = "USE " + spaceName + ";INSERT EDGE`" + "flow" + "`" + field_string + "VALUES " + from
                            + "->" + to + "@" + keyValue + ": " + value_string + ";";
              return insert_edge;
       }

       public static String JobSubmitGen(String space) {
              return "use " + space + ";" + "submit job stats;";

       }

       public static String ShowStatsGen(String space) {
              return "use " + space + ";" + "show stats";

       }

}
