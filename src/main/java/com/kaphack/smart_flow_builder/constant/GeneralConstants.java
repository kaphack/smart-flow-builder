package com.kaphack.smart_flow_builder.constant;

public class GeneralConstants {
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
