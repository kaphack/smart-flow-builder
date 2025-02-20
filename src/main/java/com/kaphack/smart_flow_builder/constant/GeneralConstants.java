package com.kaphack.smart_flow_builder.constant;

import com.kaphack.smart_flow_builder.service.ISmartFlowService;
import com.kaphack.smart_flow_builder.service.OllamaFlowService;
import com.kaphack.smart_flow_builder.service.OpenAIFlowService;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.openai.api.OpenAiApi;

import java.util.Map;

public class GeneralConstants {

  public static final Map<String, Class<? extends ISmartFlowService>> AVAILABLE_MODELS = Map.of(
      OpenAiApi.ChatModel.GPT_4_O_MINI.getName(), OpenAIFlowService.class,
      OllamaModel.LLAMA3_2.getName(), OllamaFlowService.class,
      OllamaModel.MISTRAL.getName(), OllamaFlowService.class
  );

  public static final String MODEL_NAME = "llama3.2";

  public static final String SYSTEM_TO_ASSISTANT_MESSAGE = """
          You are an AI Assistant named Flowlander, that will help to build a flow. Flow is built using nodes and edges.
          You should not respond to questions other than building the flow.
          You have four capabilities: create new flow, editing existing flow, generate flow from image and writing ES5 javascript code.
          If user asks to edit the existing flow, and if existing flow json provided in prompt use that,""" +
//          " else use function calling to get the flow and then edit and return the particular flow nodes or edges." +
          " When editing the existing flow do not change the id of the nodes or edges.";

  public static final String OUTPUT_SCHEMA = """
       {
           type: "object",
           properties: {
               status: {
                   type: "string"
               },
               flowJson: {
                   type: "object",
                   properties: {
                       nodes: {
                           type: "array",
                           items: {
                               type: "object",
                               properties: {
                                   id: {
                                       type: "string"
                                   },
                                   type: {
                                       type: "string"
                                   },
                                   data: {
                                       type: "object",
                                       properties: {
                                           // label: {
                                           //     type: "string"
                                           // },
                                           body: {
                                               type: "string"
                                           },
                                           transition: {
                                               type: "array",
                                               items: {
                                                   type: "object",
                                                   properties: {
                                                       id: {
                                                           type: "string"
                                                       },
                                                       label: {
                                                           type: "string"
                                                       }
                                                   },
                                                   additionalProperties: false
                                               }
                                           },
                                           messageList: {
                                               type: "array",
                                               items: {
                                                   type: "object",
                                                   properties: {
                                                       value: {
                                                           type: "string"
                                                       },
                                                       id: {
                                                           type: "string"
                                                       },
                                                       transitionId: {
                                                           type: "string"
                                                       }
                                                   }
                                               }
                                           },
                                           message_type: {
                                               type: "string"
                                           },
                                           variables: {
                                               type: "object",
                                               properties: {
                                                   reply_message: {
                                                       type: "string"
                                                   },
                                                   required: ["reply_message"]
                                               }
                                           },
                                           required: ["label", "transition", "variables"]
                                       }
                                   },
                                   // position: { type: "object", properties: {x: { type: "number" },y: { type: "number" }}}
                               },
                               required: ["id", "type", "data"],
                               additionalProperties: false
                           }
                       },
                       edges: {
                           type: "array",
                           items: {
                               type: "object",
                               properties: {
                                   // todo
                                   label: {
                                       type: "string"
                                   },
                                   source: {
                                       type: "string"
                                   },
                                   sourceHandle: {
                                       type: "string"
                                   },
                                   target: {
                                       type: "string"
                                   },
                                   required: ["label", "source", "sourceHandle", "target"]
                               },
                           }
                       },
                       required: ["nodes", "edges"],
                       addditionalProperties: false
                   }
               },
               message: {
                   type: "string"
               }
           },
           required: ["status", "flowJson", "message"]
       }
      """;

  public static final String TEST_OUTPUT = """
      {
          "type": "object",
          "properties": {
              "steps": {
                  "type": "array",
                  "items": {
                      "type": "object",
                      "properties": {
                          "explanation": { "type": "string" },
                          "output": { "type": "string" }
                      },
                      "required": ["explanation", "output"],
                      "additionalProperties": false
                  }
              },
              "final_answer": { "type": "string" }
          },
          "required": ["steps", "final_answer"],
          "additionalProperties": false
      }
      """;
}
